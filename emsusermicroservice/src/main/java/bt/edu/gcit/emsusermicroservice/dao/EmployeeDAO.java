package bt.edu.gcit.emsusermicroservice.dao;

import java.util.List;

import bt.edu.gcit.emsusermicroservice.entity.Employee;

public interface EmployeeDAO {
    Employee save(Employee employee);
    Employee findByEmail(String email);
    List<Employee> saveAll(List<Employee> employees); // new method
    List<Employee> findAll();
    Employee findById(Long id);
    void deleteById(Long id);
    Employee findByEmployeeId(String employeeId);
    boolean existsByEmail(String email);

}
