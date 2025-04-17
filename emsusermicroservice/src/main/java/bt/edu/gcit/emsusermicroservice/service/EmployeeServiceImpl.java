
// package bt.edu.gcit.emsusermicroservice.service;

// import bt.edu.gcit.emsusermicroservice.entity.Employee;
// import bt.edu.gcit.emsusermicroservice.dao.EmployeeDAO;
// import jakarta.transaction.Transactional;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;



// import java.util.ArrayList;
// import java.util.List;
// import java.util.UUID;

// @Service
// public class EmployeeServiceImpl implements EmployeeService {

//     @Autowired
//     private EmployeeDAO employeeDAO;

//     @Autowired
//     private EmailService emailService;  // For sending email to employee
//     @Autowired
//     private BCryptPasswordEncoder passwordEncoder;

//     public Employee addEmployee(Employee employee) {
//         // Check if email already exists
//         if (employeeDAO.findByEmail(employee.getEmail()) != null) {
//             throw new IllegalArgumentException("An employee with this email already exists.");
//         }
    
//         // Generate unique Employee ID and random password
//         String employeeId = generateEmployeeId();
//         employee.setEmployeeId(employeeId);
//         String randomPassword = generateRandomPassword();
        
//         // Save the employee to the database
//         Employee savedEmployee = employeeDAO.save(employee);
    
//         // Send email with the employee's ID and password
//         String message = "Dear " + savedEmployee.getName() + ",\n\nYour Employee ID is: " + employeeId + "\nYour Password is: " + randomPassword;
        
//         try {
//             emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
//             System.out.println("Email sent successfully to: " + savedEmployee.getEmail());
//         } catch (Exception e) {
//             System.err.println("Failed to send email: " + e.getMessage());
//             e.printStackTrace();
//         }
    
//         return savedEmployee;
//     }
    
//     @Transactional
//     @Override
//     public List<Employee> addEmployees(List<Employee> employees) {
//         List<Employee> savedEmployees = new ArrayList<>();

//         for (Employee employee : employees) {
//             String employeeId = generateEmployeeId();
//             employee.setEmployeeId(employeeId);
//             String randomPassword = generateRandomPassword();
            
//             // employee.setPassword(randomPassword); // Uncomment when password support is ready

//             Employee savedEmployee = employeeDAO.save(employee);
//             savedEmployees.add(savedEmployee);

//             String message = "Dear " + savedEmployee.getName() + ",\n\nYour Employee ID is: " + employeeId + "\nYour Password is: " + randomPassword;

//             try {
//                 emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
//                 System.out.println("Email sent successfully to: " + savedEmployee.getEmail());
//             } catch (Exception e) {
//                 System.err.println("Failed to send email to " + savedEmployee.getEmail() + ": " + e.getMessage());
//             }
//         }

//         return savedEmployees;
//     }

//     private String generateEmployeeId() {
//         return "EMP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
//     }

//     private String generateRandomPassword() {
//         return UUID.randomUUID().toString().substring(0, 8);
//     }
//     // ------------

// public List<Employee> getAllEmployees() {
//     return employeeDAO.findAll();
// }
// @Override
// public Employee getEmployeeById(Long id) {
//     return employeeDAO.findById(id);
// }
// @Override
// public boolean deleteEmployeeById(Long id) {
//     Employee employee = employeeDAO.findById(id);
//     if (employee != null) {
//         employeeDAO.deleteById(id);
//         return true;
//     }
//     return false;
// }
// @Override
// public Employee updateEmployee(Long id, Employee updatedEmployee) {
//     Employee existingEmployee = employeeDAO.findById(id);

//     if (existingEmployee == null) {
//         throw new IllegalArgumentException("Employee with ID " + id + " not found.");
//     }

//     // Update fields
//     existingEmployee.setName(updatedEmployee.getName());
//     existingEmployee.setEmail(updatedEmployee.getEmail());
//     existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
//     existingEmployee.setEmployeeTitle(updatedEmployee.getEmployeeTitle());
//     existingEmployee.setEmployeeType(updatedEmployee.getEmployeeType());
//     existingEmployee.setAchievements(updatedEmployee.getAchievements());

//     // Save updated employee
//     return employeeDAO.save(existingEmployee);
// }
// @Override
// @Transactional
// public Employee authenticate(String employeeId, String password) {
//     // Find employee by employeeId
//     Employee employee = employeeDAO.findByEmployeeId(employeeId);
    
//     // Check if employee exists and password matches (use password encoder for security)
//     if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
//         return employee;  // Return authenticated employee
//     }
    
//     return null;  // Return null if authentication fails
// }


// }

package bt.edu.gcit.emsusermicroservice.service;

import bt.edu.gcit.emsusermicroservice.entity.Employee;
import bt.edu.gcit.emsusermicroservice.dao.EmployeeDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // // Add a single employee
    // public Employee addEmployee(Employee employee) {
    //     if (employeeDAO.findByEmail(employee.getEmail()) != null) {
    //         throw new IllegalArgumentException("An employee with this email already exists.");
    //     }

    //     String employeeId = generateEmployeeId();
    //     employee.setEmployeeId(employeeId);

    //     String randomPassword = generateRandomPassword();
    //     String encodedPassword = passwordEncoder.encode(randomPassword);
    //     employee.setPassword(encodedPassword);  // ✅ Save encoded password

    //     Employee savedEmployee = employeeDAO.save(employee);

    //     String message = "Dear " + savedEmployee.getName() + ",\n\nYour Employee ID is: " + employeeId +
    //                      "\nYour Password is: " + randomPassword + "\nPlease change your password after first login.";

    //     try {
    //         emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
    //         System.out.println("Email sent successfully to: " + savedEmployee.getEmail());
    //     } catch (Exception e) {
    //         System.err.println("Failed to send email: " + e.getMessage());
    //         e.printStackTrace();
    //     }

    //     return savedEmployee;
    // }
    public Employee addEmployee(Employee employee) {
        if (employeeDAO.findByEmail(employee.getEmail()) != null) {
            System.out.println("An employee with this email already exists: " + employee.getEmail());
            return null; // Return null instead of throwing
        }
    
        String employeeId = generateEmployeeId();
        employee.setEmployeeId(employeeId);
    
        String randomPassword = generateRandomPassword();
        String encodedPassword = passwordEncoder.encode(randomPassword);
        employee.setPassword(encodedPassword);
    
        Employee savedEmployee = employeeDAO.save(employee);
    
        String message = "Dear " + savedEmployee.getName() + ",\n\nYour Employee ID is: " + employeeId +
                         "\nYour Password is: " + randomPassword + "\nPlease change your password after first login.";
    
        try {
            emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
            System.out.println("Email sent successfully to: " + savedEmployee.getEmail());
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    
        return savedEmployee;
    }
    
    // // Add multiple employees
    // @Transactional
    // @Override
    // public List<Employee> addEmployees(List<Employee> employees) {
    //     List<Employee> savedEmployees = new ArrayList<>();

    //     for (Employee employee : employees) {
    //         String employeeId = generateEmployeeId();
    //         employee.setEmployeeId(employeeId);

    //         String randomPassword = generateRandomPassword();
    //         String encodedPassword = passwordEncoder.encode(randomPassword);
    //         employee.setPassword(encodedPassword);  // ✅ Save encoded password

    //         Employee savedEmployee = employeeDAO.save(employee);
    //         savedEmployees.add(savedEmployee);

    //         String message = "Dear " + savedEmployee.getName() + ",\n\nYour Employee ID is: " + employeeId +
    //                          "\nYour Password is: " + randomPassword + "\nPlease change your password after first login.";

    //         try {
    //             emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
    //             System.out.println("Email sent successfully to: " + savedEmployee.getEmail());
    //         } catch (Exception e) {
    //             System.err.println("Failed to send email to " + savedEmployee.getEmail() + ": " + e.getMessage());
    //         }
    //     }

    //     return savedEmployees;
    // }
    @Transactional
    @Override
    public List<Employee> addEmployees(List<Employee> employees) {
        List<Employee> savedEmployees = new ArrayList<>();
    
        for (Employee employee : employees) {
            // Check if email already exists
            if (employeeDAO.existsByEmail(employee.getEmail())) {
                System.err.println("Skipped: Email already exists - " + employee.getEmail());
                continue; // Skip to next employee
            }
    
            // Generate Employee ID and password
            String employeeId = generateEmployeeId();
            employee.setEmployeeId(employeeId);
    
            String rawPassword = generateRandomPassword();
            String encodedPassword = passwordEncoder.encode(rawPassword);
            employee.setPassword(encodedPassword);
    
            // Save to DB
            Employee savedEmployee = employeeDAO.save(employee);
            savedEmployees.add(savedEmployee);
    
            // Prepare and send email
            String message = "Dear " + savedEmployee.getName() + ",\n\nYour Employee ID is: " + employeeId +
                             "\nYour Password is: " + rawPassword + "\nPlease change your password after first login.";
    
            try {
                emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
                System.out.println("✅ Email sent to: " + savedEmployee.getEmail());
            } catch (Exception e) {
                System.err.println("❌ Failed to send email to " + savedEmployee.getEmail() + ": " + e.getMessage());
            }
        }
    
        return savedEmployees;
    }
    
    // Utility: Generate random Employee ID
    private String generateEmployeeId() {
        return "EMP" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    // Utility: Generate random password
    private String generateRandomPassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }

    // Get all employees
    @Override
    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }

    // Get employee by ID
    @Override
    public Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id);
    }

    // Delete employee
    @Override
    public boolean deleteEmployeeById(Long id) {
        Employee employee = employeeDAO.findById(id);
        if (employee != null) {
            employeeDAO.deleteById(id);
            return true;
        }
        return false;
    }

    // Update employee
    @Override
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existingEmployee = employeeDAO.findById(id);

        if (existingEmployee == null) {
            throw new IllegalArgumentException("Employee with ID " + id + " not found.");
        }

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setEmployeeTitle(updatedEmployee.getEmployeeTitle());
        existingEmployee.setEmployeeType(updatedEmployee.getEmployeeType());
        existingEmployee.setAchievements(updatedEmployee.getAchievements());

        return employeeDAO.save(existingEmployee);
    }

    // Authenticate employee
    @Override
    @Transactional
    public Employee authenticate(String employeeId, String password) {
        Employee employee = employeeDAO.findByEmployeeId(employeeId);
        if (employee != null && passwordEncoder.matches(password, employee.getPassword())) {
            return employee;
        }
        return null;
    }
}
