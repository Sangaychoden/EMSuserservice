package bt.edu.gcit.emsusermicroservice.service;

import bt.edu.gcit.emsusermicroservice.entity.Employee;
import io.jsonwebtoken.io.IOException;
import bt.edu.gcit.emsusermicroservice.dao.EmployeeDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO employeeDAO;
    // private final String uploadDir = "src/main/resources/static/photos";
//   private final String uploadDir = "src/main/resources/static/images";
    private final String uploadDir = "uploads/images";  // Relative to application root
    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

  @Override
    public Employee addEmployee(Employee employee) {
        if (employeeDAO.findByEmployeeId(employee.getEmployeeId()) != null) {
            throw new IllegalArgumentException("Employee ID already exists: " + employee.getEmployeeId());
        }
        
        if (employeeDAO.findByEmail(employee.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists: " + employee.getEmail());
        }

        String randomPassword = generateRandomPassword();
        employee.setPassword(passwordEncoder.encode(randomPassword));

        Employee savedEmployee = employeeDAO.save(employee);

        String message = "Dear " + savedEmployee.getName() + ",\n\n"
                       + "Your Employee ID is: " + savedEmployee.getEmployeeId() + "\n"
                       + "Your Password is: " + randomPassword + "\n"
                       + "Please change your password after first login.";

        try {
            emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }

        return savedEmployee;
    }

    @Transactional
    @Override
    public List<Employee> addEmployees(List<Employee> employees) {
        List<Employee> savedEmployees = new ArrayList<>();

        for (Employee employee : employees) {
            try {
                if (employeeDAO.findByEmployeeId(employee.getEmployeeId()) != null) {
                    throw new IllegalArgumentException("Employee ID exists: " + employee.getEmployeeId());
                }

                if (employeeDAO.findByEmail(employee.getEmail()) != null) {
                    throw new IllegalArgumentException("Email exists: " + employee.getEmail());
                }

                String rawPassword = generateRandomPassword();
                employee.setPassword(passwordEncoder.encode(rawPassword));

                Employee savedEmployee = employeeDAO.save(employee);
                savedEmployees.add(savedEmployee);

                String message = "Dear " + savedEmployee.getName() + ",\n\n"
                               + "Your Employee ID is: " + savedEmployee.getEmployeeId() + "\n"
                               + "Your Password is: " + rawPassword + "\n"
                               + "Please change your password after first login.";

                emailService.sendEmail(savedEmployee.getEmail(), "Employee Account Details", message);
            } catch (Exception e) {
                throw new RuntimeException("Error processing employee: " + e.getMessage());
            }
        }
        return savedEmployees;
    }

  
@Transactional
@Override
public void uploadEmployeePhoto(Long id, MultipartFile photo) throws IOException, java.io.IOException {
    Employee employee = employeeDAO.findById(id);
    if (employee == null) {
        throw new IllegalArgumentException("Employee not found with id " + id);
    }

    // Create upload directory if it doesn't exist
    Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
    }

    // Generate unique filename
    String filename = UUID.randomUUID().toString() + 
                     getFileExtension(photo.getOriginalFilename());

    // Save file
    Path targetLocation = uploadPath.resolve(filename);
    Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

    // Update employee record
    employee.setPhoto(filename);
    employeeDAO.save(employee);
}
@Transactional
public void updateEmployeePhoto(Long id, MultipartFile photo) throws IOException, java.io.IOException {
      Employee employee = employeeDAO.findById(id);
    if (employee == null) {
        throw new IllegalArgumentException("Employee not found with id " + id);
    }
    
    // Delete old photo
    Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    Path oldFilePath = uploadPath.resolve(employee.getPhoto());
    Files.deleteIfExists(oldFilePath);
    
    // Save new photo
    String extension = getFileExtension(photo.getOriginalFilename());
    String filename = UUID.randomUUID().toString() + extension;
    Path targetLocation = uploadPath.resolve(filename);
    
    Files.copy(photo.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
    
    // Update record
    employee.setPhoto(filename);
    employeeDAO.save(employee);
}

private String getFileExtension(String fileName) {
    try {
        return fileName.substring(fileName.lastIndexOf("."));
    } catch (Exception e) {
        return "";
    }
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
    @Override
public Employee updateEmployee(Long id, Employee updatedEmployee) {
    Employee existingEmployee = employeeDAO.findById(id);

    if (existingEmployee == null) {
        throw new IllegalArgumentException("Employee with ID " + id + " not found.");
    }

    if (updatedEmployee.getName() != null) {
        existingEmployee.setName(updatedEmployee.getName());
    }
    if (updatedEmployee.getEmail() != null) {
        existingEmployee.setEmail(updatedEmployee.getEmail());
    }
    if (updatedEmployee.getPhoneNumber() != null) {
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
    }
    if (updatedEmployee.getEmployeeTitle() != null) {
        existingEmployee.setEmployeeTitle(updatedEmployee.getEmployeeTitle());
    }
    if (updatedEmployee.getEmployeeType() != null) {
        existingEmployee.setEmployeeType(updatedEmployee.getEmployeeType());
    }
    if (updatedEmployee.getAchievements() != null) {
        existingEmployee.setAchievements(updatedEmployee.getAchievements());
    }

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
    @Override
    public Employee getEmployeeByEmployeeId(String employeeId) {
        Employee employee = employeeDAO.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new ResourceAccessException("Employee not found with ID: " + employeeId);
        }
        return employee;
    }
    @Override
    @Transactional
    public boolean changePassword(String employeeId, String oldPassword, 
                                String newPassword, String confirmPassword) {
        // 1. Find employee
        Employee employee = employeeDAO.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        // 2. Verify old password
        if (!passwordEncoder.matches(oldPassword, employee.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        // 3. Check new password and confirmation match
        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalArgumentException("New passwords don't match");
        }

        // 4. Validate new password strength (optional)
        if (newPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        // 5. Update password
        String encodedPassword = passwordEncoder.encode(newPassword);
        employeeDAO.updatePassword(employeeId, encodedPassword);
        
        return true;
    }

}
