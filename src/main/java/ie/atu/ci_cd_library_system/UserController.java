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

    private User findByEmail(String email) {
        for (User user : users) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
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

    @PutMapping("/Update/{email}")
    public String UpdateUser(@PathVariable String email, @RequestBody User Updated){
        User existing = findByEmail(email);
        existing.setName(Updated.getName());
        existing.setEmail(Updated.getEmail());
        return "User updated successfully " + existing.getName();
    }







}