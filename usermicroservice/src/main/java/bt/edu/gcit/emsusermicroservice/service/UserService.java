package bt.edu.gcit.emsusermicroservice.service;
import bt.edu.gcit.emsusermicroservice.entity.Employee;
import bt.edu.gcit.emsusermicroservice.entity.User;
public interface UserService {
 User save(User user);
 User authenticate(String userId, String password);
 
  
}
