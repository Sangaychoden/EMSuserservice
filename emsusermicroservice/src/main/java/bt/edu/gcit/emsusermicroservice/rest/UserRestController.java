// package bt.edu.gcit.emsusermicroservice.rest;
// import bt.edu.gcit.emsusermicroservice.entity.User;
// import bt.edu.gcit.emsusermicroservice.service.UserService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestBody;
// @RestController
// @RequestMapping("/api")
// public class UserRestController {
//  private UserService userService;
//  @Autowired
//  public UserRestController(UserService userService) {
//  this.userService = userService;
//  }
//  @PostMapping("/users")
//  public User save(@RequestBody User user) {
//  return userService.save(user);
//  }
// }
package bt.edu.gcit.emsusermicroservice.rest;

import bt.edu.gcit.emsusermicroservice.entity.User;
import bt.edu.gcit.emsusermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    private UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // Register user (admin)
    @PostMapping("/admin/register")
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    // Login for admin authentication
    // @PostMapping("/admin/login")
    // public String login(@RequestParam String userId, @RequestParam String password) {
    //     User user = userService.authenticate(userId, password);
    //     if (user != null) {
    //         return "Login successful for userId: " + userId;
    //     } else {
    //         return "Invalid userId or password";
    //     }
    // }
}
