package bt.edu.gcit.emsusermicroservice.service;


public interface ForgotpasswordService {
    boolean sendOtpToEmail(String email);
    boolean verifyOtp(String email, String enteredOtp);
    boolean resetPassword(String email, String newPassword, String confirmPassword);  // Add confirmPassword
    
}

