package bt.edu.gcit.emsusermicroservice.rest;


import bt.edu.gcit.emsusermicroservice.entity.Employee;
import bt.edu.gcit.emsusermicroservice.service.EmployeeService;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
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
        // Directly return the error message in the response body
        return ResponseEntity.badRequest().body("Email already exists: " + e.getMessage());
    }
}



// @PostMapping("/bulk")
// public ResponseEntity<List<Employee>> addEmployees(@RequestBody List<Employee> employees) {
//     try {
//         List<Employee> saved = employeeService.addEmployees(employees);
//         return ResponseEntity.ok(saved);
//     } catch (IllegalArgumentException e) {
//         return ResponseEntity.badRequest().body(null); // or return a custom error message
//     }
// }
@PostMapping("/bulk")
public ResponseEntity<?> addEmployees(@RequestBody List<Employee> employees) {
    try {
        List<Employee> saved = employeeService.addEmployees(employees);
        return ResponseEntity.ok(saved);
    } catch (IllegalArgumentException e) {
        // Directly return the error message in the response body
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
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
// @PostMapping("/login")
//     public String login(@RequestParam String employeeId, @RequestParam String password) {
//         // Call authenticate method to verify login
//         Employee employee = employeeService.authenticate(employeeId, password);
        
//         if (employee != null) {
//             return "Login successful for employee ID: " + employee.getEmployeeId();
//         } else {
//             return "Invalid employee ID or password";
//         }
//     }

}
