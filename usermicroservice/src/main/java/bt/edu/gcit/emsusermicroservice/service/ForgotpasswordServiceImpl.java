package bt.edu.gcit.emsusermicroservice.service;

import bt.edu.gcit.emsusermicroservice.entity.User;
import bt.edu.gcit.emsusermicroservice.entity.Employee;
import bt.edu.gcit.emsusermicroservice.dao.UserDAO;
import bt.edu.gcit.emsusermicroservice.dao.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

@Service
public class ForgotpasswordServiceImpl implements ForgotpasswordService {

    private final UserDAO userDAO;
    private final EmployeeDAO employeeDAO;
    private final JavaMailSender mailSender;
    private final BCryptPasswordEncoder passwordEncoder;

    private final ConcurrentHashMap<String, OtpInfo> otpStorage = new ConcurrentHashMap<>();
    private static final long OTP_VALID_DURATION_SECONDS = 5 * 60; // 5 minutes

    @Autowired
    public ForgotpasswordServiceImpl(UserDAO userDAO, EmployeeDAO employeeDAO,
                                     JavaMailSender mailSender, BCryptPasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.employeeDAO = employeeDAO;
        this.mailSender = mailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean sendOtpToEmail(String email) {
        Optional<User> userOptional = userDAO.findByEmail(email);
        if (userOptional.isPresent()) {
            return sendOtp(email);
        }

        Employee employee = employeeDAO.findByEmail(email);
        if (employee != null) {
            return sendOtp(email);
        }

        return false;
    }

    private boolean sendOtp(String email) {
        String otp = generateOtp();
        otpStorage.put(email, new OtpInfo(otp, Instant.now()));

        try {
            sendOtpEmail(email, otp);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String generateOtp() {
        Random rand = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            otp.append(rand.nextInt(10));
        }
        return otp.toString();
    }

    private void sendOtpEmail(String email, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Your OTP for Password Reset");
        helper.setText("Your OTP for resetting your password is: " + otp + "\n\nNote: This OTP is valid for 5 minutes.");
        mailSender.send(message);
    }

    @Override
    public boolean verifyOtp(String email, String enteredOtp) {
        OtpInfo otpInfo = otpStorage.get(email);

        if (otpInfo != null) {
            Instant now = Instant.now();
            if (now.isBefore(otpInfo.generatedAt.plusSeconds(OTP_VALID_DURATION_SECONDS)) &&
                otpInfo.otp.equals(enteredOtp)) {
                otpStorage.remove(email);
                return true;
            } else {
                otpStorage.remove(email);
            }
        }
        return false;
    }

    @Transactional
    @Override
    public boolean resetPassword(String email, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return false;
        }

        Optional<User> userOptional = userDAO.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setPassword(passwordEncoder.encode(newPassword));
            userDAO.save(user);
            otpStorage.remove(email);
            return true;
        }

        Employee employee = employeeDAO.findByEmail(email);
        if (employee != null) {
            employee.setPassword(passwordEncoder.encode(newPassword));
            employeeDAO.save(employee);
            otpStorage.remove(email);
            return true;
        }

        return false;
    }

    private static class OtpInfo {
        String otp;
        Instant generatedAt;

        OtpInfo(String otp, Instant generatedAt) {
            this.otp = otp;
            this.generatedAt = generatedAt;
        }
    }
}
