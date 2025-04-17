package bt.edu.gcit.emsusermicroservice.service;
import bt.edu.gcit.emsusermicroservice.dao.UserDAO;
import bt.edu.gcit.emsusermicroservice.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class UserServiceImpl implements UserService {
 private UserDAO userDAO;
 private BCryptPasswordEncoder passwordEncoder;
 @Autowired
 public UserServiceImpl(UserDAO userDAO) {
 this.userDAO = userDAO;
 this.passwordEncoder = new BCryptPasswordEncoder();
 }
 @Override
 @Transactional
 public User save(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
 return userDAO.save(user);
 }
 @Override
    @Transactional
    public User authenticate(String userId, String password) {
        User user = userDAO.findByUserId(userId);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;  // Return authenticated user
        }
        return null;  // Return null if authentication fails
    }
    
}
