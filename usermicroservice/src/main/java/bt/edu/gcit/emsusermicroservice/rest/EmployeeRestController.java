package bt.edu.gcit.emsusermicroservice.rest;


import bt.edu.gcit.emsusermicroservice.entity.Employee;
import bt.edu.gcit.emsusermicroservice.service.EmployeeService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employee")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;


@PostMapping("/add")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        try {
            Employee savedEmployee = employeeService.addEmployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Server error: " + e.getMessage()));
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<?> addEmployees(@RequestBody List<Employee> employees) {
        try {
            List<Employee> saved = employeeService.addEmployees(employees);
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Server error: " + e.getMessage()));
        }
    }

    @PostMapping("/{id}/upload-photo")
    public ResponseEntity<?> uploadEmployeePhoto(
            @PathVariable Long id,
            @RequestParam("photo") MultipartFile photo) {
        
        try {
            employeeService.uploadEmployeePhoto(id, photo);
            Employee updated = employeeService.getEmployeeById(id);
            
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Photo uploaded successfully",
                "employeeId", id,
                "photoUrl", "/images/" + updated.getPhoto()
            ));
            
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", e.getMessage(),
                "employeeId", id
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "File upload failed: " + e.getMessage(),
                "employeeId", id
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of(
                "status", "error",
                "message", "Unexpected error: " + e.getMessage(),
                "employeeId", id
            ));
        }
    }
// update
@PutMapping("/{id}/photo")
public ResponseEntity<?> updateEmployeePhoto(
        @PathVariable Long id,
        @RequestParam("photo") MultipartFile photo) {
    
    try {
        Employee employee = employeeService.getEmployeeById(id);
        
        // Check if employee has existing photo
        if (employee.getPhoto() == null || employee.getPhoto().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of(
                "status", "error",
                "message", "No existing photo to update",
                "employeeId", id
            ));
        }
        
        employeeService.uploadEmployeePhoto(id, photo);
        Employee updated = employeeService.getEmployeeById(id);
        
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Photo updated successfully",
            "employeeId", id,
            "photoUrl", "/images/" + updated.getPhoto()
        ));
        
    } catch (Exception e) {
        return ResponseEntity.internalServerError().body(Map.of(
            "status", "error",
            "message", e.getMessage(),
            "employeeId", id
        ));
    }
}

// Get all employees
@GetMapping("/all")
public ResponseEntity<List<Employee>> getAllEmployees() {
    List<Employee> employees = employeeService.getAllEmployees();
    return ResponseEntity.ok(employees);
}
// Get employee by ID
@GetMapping("/{id}")
public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
    Employee employee = employeeService.getEmployeeById(id);
    if (employee != null) {
        return ResponseEntity.ok(employee);
    } else {
        return ResponseEntity.notFound().build();
    }
}
// Delete employee by ID
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
    boolean deleted = employeeService.deleteEmployeeById(id);
    if (deleted) {
        return ResponseEntity.ok("Employee deleted successfully.");
    } else {
        return ResponseEntity.notFound().build();
    }
}
@PutMapping("/update/{id}")
public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee) {
    try {
        Employee employee = employeeService.updateEmployee(id, updatedEmployee);
        return ResponseEntity.ok(employee);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    }
}

   @GetMapping("/by-employee-id/{employeeId}")
    public ResponseEntity<?> getEmployeeByEmployeeId(@PathVariable String employeeId) {
        try {
            Employee employee = employeeService.getEmployeeByEmployeeId(employeeId);
            return ResponseEntity.ok(employee);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
 @PostMapping("/{employeeId}/change-password")
    public ResponseEntity<?> changePassword(
            @PathVariable String employeeId,
            @RequestBody PasswordChangeRequest request) {
        
        try {
            employeeService.changePassword(
                employeeId,
                request.getOldPassword(),
                request.getNewPassword(),
                request.getConfirmPassword()
            );
            return ResponseEntity.ok("Password changed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public static class PasswordChangeRequest {
        private String oldPassword;
        private String newPassword;
        private String confirmPassword;

        // Getters and Setters
        public String getOldPassword() {
            return oldPassword;
        }

        public void setOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }

        public String getConfirmPassword() {
            return confirmPassword;
        }

        public void setConfirmPassword(String confirmPassword) {
            this.confirmPassword = confirmPassword;
        }
    }
    @GetMapping("/names")
public ResponseEntity<List<Map<String, Object>>> getAllEmployeeNamesWithIds() {
    List<Employee> employees = employeeService.getAllEmployees();
    
    // Transform to list of maps with only id and name
    List<Map<String, Object>> result = employees.stream()
        .map(employee -> {
            Map<String, Object> empMap = new HashMap<>();
            empMap.put("employeeId", employee.getEmployeeId()); // assuming this is the unique employee ID field
            empMap.put("name", employee.getName()); // assuming there's a getName() method
            return empMap;
        })
        .collect(Collectors.toList());
    
    return ResponseEntity.ok(result);
}
}

