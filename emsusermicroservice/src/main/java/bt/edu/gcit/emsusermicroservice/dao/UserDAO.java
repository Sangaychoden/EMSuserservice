// // package bt.edu.gcit.emsusermicroservice.dao;
// // import bt.edu.gcit.emsusermicroservice.entity.User;
// // public interface UserDAO{
// //  User save(User user);
// // }
// package bt.edu.gcit.emsusermicroservice.dao;

// import java.util.Optional;

// import bt.edu.gcit.emsusermicroservice.entity.User;

// public interface UserDAO {
//     User save(User user);
//     User findByUserId(String userId);  // Find user by userId
//     User findByEmail(String email);
// }
package bt.edu.gcit.emsusermicroservice.dao;

import bt.edu.gcit.emsusermicroservice.entity.User;
import java.util.Optional;

public interface UserDAO {
    User save(User user);
    User findByUserId(String userId);
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
