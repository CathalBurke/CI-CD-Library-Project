package ie.atu.ci_cd_library_system;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // Add user with validation
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user) {
        users.add(user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User added successfully: " + user.getName());
    }

    // List all users
    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(users);
    }
}
