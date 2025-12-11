package ie.atu.ci_cd_library_system.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    // Minimal endpoint for Loan service
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> userExists(@PathVariable Long id) {
        boolean exists = userRepository.existsById(id);
        return ResponseEntity.ok(exists);
    }

    private boolean isAdmin(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(User::isAdmin)
                .filter(user -> user.getPassword().equals(password))
                .isPresent();
    }


    // Add user with validation and save to DB
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
        user.setAdmin(false);
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    // List all users from DB
    @GetMapping("/list")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Admin-only: update an existing user by id
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword,
            @Valid @RequestBody User updatedUser
    ) {
        // Check admin credentials first
        if (!isAdmin(adminEmail, adminPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only admin users can update users");
        }

        return userRepository.findById(id)
                .map(existing -> {
                    existing.setName(updatedUser.getName());
                    existing.setEmail(updatedUser.getEmail());
                    existing.setPassword(updatedUser.getPassword());
                    existing.setAdmin(updatedUser.isAdmin());
                    User saved = userRepository.save(existing);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Admin-only: delete a user by id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable Long id,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword
    ) {
        // Check admin credentials first
        if (!isAdmin(adminEmail, adminPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only admin users can delete users");
        }

        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
