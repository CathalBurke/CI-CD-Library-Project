package ie.atu.ci_cd_library_system;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController();
    }

    @Test
    void testAddUserSuccess() {
        User user = new User("John Doe", "john@example.com");

        ResponseEntity<?> response = userController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("User added successfully: John Doe"));
    }

    @Test
    void testListUsersInitiallyEmpty() {
        ResponseEntity<List<User>> response = userController.listUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testListUsersAfterAdding() {
        User user1 = new User("Alice", "alice@example.com");
        User user2 = new User("Bob", "bob@example.com");

        userController.addUser(user1);
        userController.addUser(user2);

        ResponseEntity<List<User>> response = userController.listUsers();

        assertEquals(2, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getName());
        assertEquals("Bob", response.getBody().get(1).getName());
    }
}