// package bt.edu.gcit.emsusermicroservice.dao;

// import bt.edu.gcit.emsusermicroservice.entity.Employee;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Repository;

// import jakarta.persistence.EntityManager;
// import jakarta.persistence.NoResultException;
// import jakarta.persistence.PersistenceContext;
// import jakarta.transaction.Transactional;

// @Repository
// @Transactional
// public class EmployeeDAOImpl implements EmployeeDAO {

//     @PersistenceContext
//     private EntityManager entityManager;

//     @Override
//     public Employee save(Employee employee) {
//         if (employee.getId() == null) {
//             // If employee has no ID, it's a new entity, so persist it
//             entityManager.persist(employee);
//             return employee;
//         } else {
//             // If employee has an ID, it's an existing entity, so merge it
//             return entityManager.merge(employee);
//         }
//     }


//     @Override
// public Employee findByEmail(String email) {
//     String query = "SELECT e FROM Employee e WHERE e.email = :email";
//     try {
//         return entityManager.createQuery(query, Employee.class)
//                             .setParameter("email", email)
//                             .getSingleResult();
//     } catch (Exception e) {
//         return null; // No employee found with the given email
//     }
// }

//     @Override
// public List<Employee> saveAll(List<Employee> employees) {
//     for (Employee employee : employees) {
//         if (employee.getId() == null) {
//             entityManager.persist(employee);
//         } else {
//             entityManager.merge(employee);
//         }
//     }
//     return employees;
// }
// // ------------
// @Override
// public List<Employee> findAll() {
//     String query = "SELECT e FROM Employee e";
//     return entityManager.createQuery(query, Employee.class).getResultList();
// }
// @Override
// public Employee findById(Long id) {
//     return entityManager.find(Employee.class, id);
// }
// @Override
// public void deleteById(Long id) {
//     Employee employee = entityManager.find(Employee.class, id);
//     if (employee != null) {
//         entityManager.remove(employee);
//     }
// }
//  @Override
//     public Employee findByEmployeeId(String employeeId) {
//         try {
//             // Using JPQL to find the employee by employeeId
//             String jpql = "SELECT e FROM Employee e WHERE e.employeeId = :employeeId";
//             return entityManager.createQuery(jpql, Employee.class)
//                                 .setParameter("employeeId", employeeId)
//                                 .getSingleResult();  // Returns single result
//         } catch (NoResultException e) {
//             return null;  // Return null if no employee is found
//         }
//     }

// }
package bt.edu.gcit.emsusermicroservice.dao;

import bt.edu.gcit.emsusermicroservice.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class EmployeeDAOImpl implements EmployeeDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee save(Employee employee) {
        if (employee.getId() == null) {
            entityManager.persist(employee);
            return employee;
        } else {
            return entityManager.merge(employee);
        }
    }

    @Override
    public Employee findByEmail(String email) {
        try {
            String query = "SELECT e FROM Employee e WHERE e.email = :email";
            return entityManager.createQuery(query, Employee.class)
                                .setParameter("email", email)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<Employee> saveAll(List<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getId() == null) {
                entityManager.persist(employee);
            } else {
                entityManager.merge(employee);
            }
        }
        return employees;
    }

    @Override
    public List<Employee> findAll() {
        return entityManager.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
    }

    @Override
    public Employee findById(Long id) {
        return entityManager.find(Employee.class, id);
    }

    @Override
    public void deleteById(Long id) {
        Employee employee = entityManager.find(Employee.class, id);
        if (employee != null) {
            entityManager.remove(employee);
        }
    }

    @Override
    public Employee findByEmployeeId(String employeeId) {
        try {
            String jpql = "SELECT e FROM Employee e WHERE e.employeeId = :employeeId";
            return entityManager.createQuery(jpql, Employee.class)
                                .setParameter("employeeId", employeeId)
                                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    @Override
public boolean existsByEmail(String email) {
    String query = "SELECT COUNT(e) FROM Employee e WHERE e.email = :email";
    Long count = entityManager.createQuery(query, Long.class)
                              .setParameter("email", email)
                              .getSingleResult();
    return count > 0;
}
  @Override
    public void updatePassword(String employeeId, String newPassword) {
        Employee employee = findByEmployeeId(employeeId);
        if (employee != null) {
            employee.setPassword(newPassword);
            entityManager.merge(employee);
        }
    }
}
