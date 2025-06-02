// package bt.edu.gcit.emsusermicroservice.dao;
// import bt.edu.gcit.emsusermicroservice.entity.User;
// import org.springframework.stereotype.Repository;
// import jakarta.persistence.EntityManager;
// import org.springframework.beans.factory.annotation.Autowired;
// @Repository
// public class UserDAOImpl implements UserDAO {
//  private EntityManager entityManager;
//  @Autowired
//  public UserDAOImpl(EntityManager entityManager) {
//  this.entityManager = entityManager;
//  }
//  @Override
//  public User save(User user) {
//  return entityManager.merge(user);
//  }
// }
package bt.edu.gcit.emsusermicroservice.dao;

import bt.edu.gcit.emsusermicroservice.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Optional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    // @Override
    // public User save(User user) {
    //     if (user.getId() == null) {
    //         entityManager.persist(user);
    //         return user;
    //     } else {
    //         return entityManager.merge(user);
    //     }
    // }
    @Override
     public User save(User user) {
     return entityManager.merge(user);
     }

    @Override
    public User findByUserId(String userId) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();
        } catch (Exception e) {
            return null;  // Return null if user not found
        }
    }
    @Override
    public Optional<User> findByEmail(String email) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return Optional.of(user);
        } catch (Exception e) {
            return Optional.empty(); // Return empty Optional if user not found
        }
    }
    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(e) FROM Employee e WHERE e.email = :email";
        Long count = entityManager.createQuery(query, Long.class)
                                  .setParameter("email", email)
                                  .getSingleResult();
        return count > 0;
    }
}
