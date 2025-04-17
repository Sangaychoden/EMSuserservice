package bt.edu.gcit.emsusermicroservice.service;

import java.util.List;

import bt.edu.gcit.emsusermicroservice.entity.Employee;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface EmployeeService {
    Employee addEmployee(Employee employee);
    List<Employee> addEmployees(List<Employee> employees); 
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Long id);
    boolean deleteEmployeeById(Long id);
    Employee updateEmployee(Long id, Employee updatedEmployee);
    // void uploadEmployeePhoto(int id, MultipartFile photo) throws IOException;
    // Employee authenticate(String userId, String password);
    Employee authenticate(String userId, String password);
   
   
}