// package bt.edu.gcit.emsusermicroservice.rest;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import bt.edu.gcit.emsusermicroservice.entity.Employee;
// import bt.edu.gcit.emsusermicroservice.entity.User;
// import bt.edu.gcit.emsusermicroservice.service.EmployeeService;
// import bt.edu.gcit.emsusermicroservice.service.UserService;

// @RestController
// @RequestMapping("/api") // You can customize the base path
// public class LoginController {

//     @Autowired
//     private EmployeeService employeeService;

//     @Autowired
//     private UserService userService;

//     @PostMapping("/login")
//     public ResponseEntity<String> login(@RequestParam String userId, @RequestParam String password) {
//         // Try admin first
//         User user = userService.authenticate(userId, password);
//         if (user != null) {
//             return ResponseEntity.ok("Admin login successful for userId: " + userId);
//         }

//         // Try employee if not admin
//         Employee employee = employeeService.authenticate(userId, password);
//         if (employee != null) {
//             return ResponseEntity.ok("Employee login successful for employeeId: " + employee.getEmployeeId());
//         }

//         // If neither worked
//         return ResponseEntity.status(401).body("Invalid credentials");
//     }
// }
package bt.edu.gcit.emsusermicroservice.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import bt.edu.gcit.emsusermicroservice.entity.Employee;
import bt.edu.gcit.emsusermicroservice.entity.User;
import bt.edu.gcit.emsusermicroservice.service.EmployeeService;
import bt.edu.gcit.emsusermicroservice.service.UserService;
import bt.edu.gcit.emsusermicroservice.service.ForgotpasswordService;

@RestController
@RequestMapping("/api") // You can customize the base path
public class LoginController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private UserService userService;

    @Autowired
    private ForgotpasswordService forgotpasswordService; // Inject the ForgotpasswordService

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String userId, @RequestParam String password) {
        // Try admin first
        User user = userService.authenticate(userId, password);
        if (user != null) {
            return ResponseEntity.ok("Admin login successful for userId: " + userId);
        }

        // Try employee if not admin
        Employee employee = employeeService.authenticate(userId, password);
        if (employee != null) {
            return ResponseEntity.ok("Employee login successful for employeeId: " + employee.getEmployeeId());
        }

        // If neither worked
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // Endpoint to send OTP for password reset
    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendOtpToEmail(@RequestParam String email) {
        boolean otpSent = forgotpasswordService.sendOtpToEmail(email);
        if (otpSent) {
            return ResponseEntity.ok("OTP sent to email: " + email);
        }
        return ResponseEntity.status(404).body("Email not found");
    }

    // Endpoint to verify OTP
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        boolean isValid = forgotpasswordService.verifyOtp(email, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        }
        return ResponseEntity.status(400).body("Invalid OTP or OTP expired");
    }

    // Endpoint to reset password
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String email,
                                                @RequestParam String newPassword,
                                                @RequestParam String confirmPassword) {
        boolean isReset = forgotpasswordService.resetPassword(email, newPassword, confirmPassword);
        if (isReset) {
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.status(400).body("Password reset failed");
    }
}
