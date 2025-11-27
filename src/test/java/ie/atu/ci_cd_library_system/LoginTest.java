package ie.atu.ci_cd_library_system;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class LoginValidationTest {

    private static Validator validator;

    @BeforeAll
    static void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void UsernameBlank_Fails() {
        Login login = new Login("", "password123");
        Set<ConstraintViolation<Login>> violations = validator.validate(login);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().equals("Username cannot be blank")));
    }

    @Test
    void PasswordTooShort_Fails() {
        Login login = new Login("user", "a");
        Set<ConstraintViolation<Login>> violations = validator.validate(login);

        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("Password must be between")));
    }

    @Test
    void UsernameAndPasswordValid_Passes() {
        Login login = new Login("validUser", "validPass");
        Set<ConstraintViolation<Login>> violations = validator.validate(login);

        assertTrue(violations.isEmpty());
    }
}