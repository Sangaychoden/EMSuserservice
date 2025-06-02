// package bt.edu.gcit.emsusermicroservice.service;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import bt.edu.gcit.emsusermicroservice.entity.Employee;
// import bt.edu.gcit.emsusermicroservice.entity.User;

// @Service
// public class AuthenticationService {

//     @Autowired
//     private UserService userService; // Your UserService
//     @Autowired
//     private EmployeeService employeeService; // Your EmployeeService

//     public String authenticate(String userId, String password) {
//         // Check if the userId matches a User
//         User user = userService.authenticate(userId, password);
//         if (user != null) {
//             return "Login successful for userId: " + userId;
//         }

//         // Check if the userId matches an Employee
//         Employee employee = employeeService.authenticate(userId, password);
//         if (employee != null) {
//             return "Login successful for employeeId: " + userId;
//         }

//         // If neither match, return invalid credentials
//         return "Invalid userId or password";
//     }
// }
