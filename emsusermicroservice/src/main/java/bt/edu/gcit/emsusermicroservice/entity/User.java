
// // package bt.edu.gcit.emsusermicroservice.entity;

// // import jakarta.persistence.Entity;
// // import jakarta.persistence.Table;
// // import jakarta.persistence.GeneratedValue;
// // import jakarta.persistence.GenerationType;
// // import jakarta.persistence.Id;
// // import jakarta.persistence.Column;
// // import jakarta.persistence.ManyToMany;
// // import jakarta.persistence.FetchType;
// // import jakarta.persistence.JoinTable;
// // import jakarta.persistence.JoinColumn;

// // import java.util.HashSet;
// // import java.util.Set;

// // @Entity
// // @Table(name = "users")
// // public class User {

// //     @Id
// //     @GeneratedValue(strategy = GenerationType.IDENTITY)
// //     private Long id;

// //     @Column(length = 8, nullable = false, unique = true)  // 8-digit user ID
// //     private String userId;

// //     @Column(nullable = false)
// //     private String password;

// //     @ManyToMany(fetch = FetchType.EAGER)
// //     @JoinTable(
// //         name = "users_roles",
// //         joinColumns = @JoinColumn(name = "user_id"),
// //         inverseJoinColumns = @JoinColumn(name = "role_id")
// //     )
// //     private Set<Role> roles = new HashSet<>();

// //     // Constructors
// //     public User() {}

// //     public User(String userId, String password, Set<Role> roles) {
// //         this.userId = userId;
// //         this.password = password;
// //         this.roles = roles;
// //     }

// //     // Getters and Setters
// //     public Long getId() {
// //         return id;
// //     }

// //     public void setId(Long id) {
// //         this.id = id;
// //     }

// //     public String getUserId() {
// //         return userId;
// //     }

// //     public void setUserId(String userId) {
// //         this.userId = userId;
// //     }

// //     public String getPassword() {
// //         return password;
// //     }

// //     public void setPassword(String password) {
// //         this.password = password;
// //     }

// //     public Set<Role> getRoles() {
// //         return roles;
// //     }

// //     public void setRoles(Set<Role> roles) {
// //         this.roles = roles;
// //     }

// //     public void addRole(Role role) {
// //         if (role != null) {
// //             this.roles.add(role);
// //         }
// //     }
// // }
// package bt.edu.gcit.emsusermicroservice.entity;

// import jakarta.persistence.Entity;
// import jakarta.persistence.Table;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.Column;

// @Entity
// @Table(name = "users")
// public class User {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @Column(length = 8, nullable = false, unique = true)  // 8-digit user ID
//     private String userId;

//     @Column(nullable = false)
//     private String password;

//     // Constructors
//     public User() {}

//     public User(String userId, String password) {
//         this.userId = userId;
//         this.password = password;
//     }

//     // Getters and Setters
//     public Long getId() {
//         return id;
//     }

//     public void setId(Long id) {
//         this.id = id;
//     }

//     public String getUserId() {
//         return userId;
//     }

//     public void setUserId(String userId) {
//         this.userId = userId;
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
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 8, nullable = false, unique = true)  // 8-digit user ID
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)  // Email must be unique
    private String email;

    @Column(length = 500)  // Length of details can be adjusted as needed
    private String details;

    // Constructors
    public User() {}

    public User(String userId, String password, String email, String details) {
        this.userId = userId;
        this.password = password;
        this.email = email;
        this.details = details;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
