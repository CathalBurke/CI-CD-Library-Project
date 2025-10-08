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
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            return "Name is required";
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            return "Email is required";
        }
        if (!user.getEmail().contains("@") || !user.getEmail().contains(".com") && !user.getEmail().contains(".ie") && !user.getEmail().contains(".org") && !user.getEmail().contains(".co.uk")) {
            return "Invalid email format";
        }

        users.add(user);
        return "User added successfully " + user.getName();
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return users;
    }
}