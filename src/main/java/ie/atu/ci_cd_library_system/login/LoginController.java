package ie.atu.ci_cd_library_system.login;

import ie.atu.ci_cd_library_system.user.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody Login login) {
        return userRepository.findByEmail(login.getEmail())
                .filter(user -> user.getPassword().equals(login.getPassword()))
                .map(user -> ResponseEntity.ok(
                        new LoginResponse("Login successful! Welcome, " + user.getName())
                ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new LoginResponse("Login unsuccessful — invalid email or password.")));
    }
}
