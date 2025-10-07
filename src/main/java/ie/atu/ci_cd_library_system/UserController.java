package ie.atu.ci_cd_library_system;

import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final List<User> users;

    public UserController() {
        this.users = new ArrayList<>();
    }

    @PostMapping("/add")
    public String addUser(@RequestBody User user) {
        users.add(user);
        return "User added: " + user.getName();
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return users;
    }
}