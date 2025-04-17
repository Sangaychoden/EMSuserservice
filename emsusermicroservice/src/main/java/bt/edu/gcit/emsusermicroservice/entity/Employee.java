// // package bt.edu.gcit.emsusermicroservice.entity;

// // import jakarta.persistence.Entity;
// // import jakarta.persistence.Id;
// // import jakarta.persistence.GeneratedValue;
// // import jakarta.persistence.GenerationType;

// // @Entity
// // public class Employee {

// //     @Id
// //     @GeneratedValue(strategy = GenerationType.IDENTITY)
// //     private Long id;

// //     private String employeeId;
// //     private String name;
// //     private String email;
// //     private String phoneNumber;
// //     private String employeeTitle;
// //     private String employeeType;
// //     private String achievements;
// //     private String photo;

// //     // Getters and Setters

// //     public Long getId() {
// //         return id;
// //     }

// //     public void setId(Long id) {
// //         this.id = id;
// //     }

// //     public String getEmployeeId() {
// //         return employeeId;
// //     }

// //     public void setEmployeeId(String employeeId) {
// //         this.employeeId = employeeId;
// //     }

// //     public String getName() {
// //         return name;
// //     }

// //     public void setName(String name) {
// //         this.name = name;
// //     }

// //     public String getEmail() {
// //         return email;
// //     }

// //     public void setEmail(String email) {
// //         this.email = email;
// //     }

// //     public String getPhoneNumber() {
// //         return phoneNumber;
// //     }

// //     public void setPhoneNumber(String phoneNumber) {
// //         this.phoneNumber = phoneNumber;
// //     }

// //     public String getEmployeeTitle() {
// //         return employeeTitle;
// //     }

// //     public void setEmployeeTitle(String employeeTitle) {
// //         this.employeeTitle = employeeTitle;
// //     }

// //     public String getEmployeeType() {
// //         return employeeType;
// //     }

// //     public void setEmployeeType(String employeeType) {
// //         this.employeeType = employeeType;
// //     }

// //     public String getAchievements() {
// //         return achievements;
// //     }

// //     public void setAchievements(String achievements) {
// //         this.achievements = achievements;
// //     }
// //     public String getPhoto() {
// //         return photo;
// //     }

// //     public void setPhoto(String photo) {
// //         this.photo = photo;
// //     }

// // }
// package bt.edu.gcit.emsusermicroservice.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Id;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;

// @Entity
// public class Employee {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String employeeId;
//     private String name;
//     private String email;
//     private String phoneNumber;
//     private String employeeTitle;
//     private String employeeType;
//     private String achievements;
//     private String photo;
    
//     private String password;  // Add password field

//     // Getters and Setters

//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getEmployeeId() {
//         return employeeId;
//     }

//     public void setEmployeeId(String employeeId) {
//         this.employeeId = employeeId;
//     }

//     public String getName() {
//         return name;
//     }

//     public void setName(String name) {
//         this.name = name;
//     }

//     public String getEmail() {
//         return email;
//     }

//     public void setEmail(String email) {
//         this.email = email;
//     }

//     public String getPhoneNumber() {
//         return phoneNumber;
//     }

//     public void setPhoneNumber(String phoneNumber) {
//         this.phoneNumber = phoneNumber;
//     }

//     public String getEmployeeTitle() {
//         return employeeTitle;
//     }

//     public void setEmployeeTitle(String employeeTitle) {
//         this.employeeTitle = employeeTitle;
//     }

//     public String getEmployeeType() {
//         return employeeType;
//     }

//     public void setEmployeeType(String employeeType) {
//         this.employeeType = employeeType;
//     }

//     public String getAchievements() {
//         return achievements;
//     }

//     public void setAchievements(String achievements) {
//         this.achievements = achievements;
//     }

//     public String getPhoto() {
//         return photo;
//     }

//     public void setPhoto(String photo) {
//         this.photo = photo;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }
// }
package bt.edu.gcit.emsusermicroservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Column(length = 9, nullable = false, unique = true)  // 8-digit employee ID
    // private String employeeId;
    
    @Column(name = "employee_id")
    private String employeeId; 

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String employeeTitle;

    @Column(nullable = false)
    private String employeeType;

    @Column(nullable = true)
    private String achievements;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = false)
    private String password;

    // Constructors
    public Employee() {}

    public Employee(String employeeId, String name, String email, String phoneNumber, String employeeTitle, 
                    String employeeType, String achievements, String photo, String password) {
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.employeeTitle = employeeTitle;
        this.employeeType = employeeType;
        this.achievements = achievements;
        this.photo = photo;
        this.password = password;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmployeeTitle() {
        return employeeTitle;
    }

    public void setEmployeeTitle(String employeeTitle) {
        this.employeeTitle = employeeTitle;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

  
}
