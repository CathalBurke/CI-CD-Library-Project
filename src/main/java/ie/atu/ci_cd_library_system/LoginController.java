package ie.atu.ci_cd_library_system;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api")
@RestController
public class LoginController {

    // Hardcoded list of admin users (no permissions yet, just basic login)
    private final List<Login> users = Arrays.asList(
            new Login("patrick", "password"),
            new Login("cathal", "password"),
            new Login("fionn", "password"),
            new Login("enzo", "password")
    );

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody Login login) {
        // Loop through hardcoded users
        for (Login user : users) {
            if (user.getUsername().equalsIgnoreCase(login.getUsername())
                    && user.getPassword().equals(login.getPassword())) {
                return ResponseEntity.ok(new LoginResponse("Login successful! Welcome, " + user.getUsername()));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse("Login unsuccessful — invalid credentials."));
    }
}
