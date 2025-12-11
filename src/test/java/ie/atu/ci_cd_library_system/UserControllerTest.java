package ie.atu.ci_cd_library_system;

import ie.atu.ci_cd_library_system.user.User;
import ie.atu.ci_cd_library_system.user.UserController;
import ie.atu.ci_cd_library_system.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepositoryMock;

    @BeforeEach
    void setUp() {
        userRepositoryMock = mock(UserRepository.class);

        List<User> inMemory = new ArrayList<>();
        AtomicLong idGenerator = new AtomicLong(1);

        when(userRepositoryMock.save(any(User.class))).thenAnswer((Answer<User>) invocation -> {
            User u = invocation.getArgument(0);
            if (u.getId() == null) {
                u.setId(idGenerator.getAndIncrement());
            }
            inMemory.add(u);
            return u;
        });


        when(userRepositoryMock.findAll()).thenAnswer((Answer<List<User>>) invocation -> new ArrayList<>(inMemory));


        when(userRepositoryMock.findById(anyLong())).thenAnswer((Answer<Optional<User>>) invocation -> {
            Long id = invocation.getArgument(0);
            return inMemory.stream().filter(u -> id.equals(u.getId())).findFirst();
        });


        when(userRepositoryMock.existsById(anyLong())).thenAnswer((Answer<Boolean>) invocation -> {
            Long id = invocation.getArgument(0);
            return inMemory.stream().anyMatch(u -> id.equals(u.getId()));
        });


        doAnswer((Answer<Void>) invocation -> {
            Long id = invocation.getArgument(0);
            inMemory.removeIf(u -> id.equals(u.getId()));
            return null;
        }).when(userRepositoryMock).deleteById(anyLong());


        when(userRepositoryMock.findByEmail(anyString())).thenAnswer((Answer<Optional<User>>) invocation -> {
            String email = invocation.getArgument(0);
            return inMemory.stream().filter(u -> email.equals(u.getEmail())).findFirst();
        });

        userController = new UserController(userRepositoryMock);
    }

    @Test
    void testAddUserSuccess() {
        User user = new User("John Doe", "john@example.com");
        user.setPassword("password123");

        ResponseEntity<User> response = userController.addUser(user);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getName());
        assertEquals("john@example.com", response.getBody().getEmail());
        assertNotNull(response.getBody().getId());
    }

    @Test
    void testListUsersInitiallyEmpty() {
        ResponseEntity<List<User>> response = userController.listUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }

    @Test
    void testListUsersAfterAdding() {
        User user1 = new User("Alice", "alice@example.com");
        user1.setPassword("pw1");
        User user2 = new User("Bob", "bob@example.com");
        user2.setPassword("pw2");

        userController.addUser(user1);
        userController.addUser(user2);

        ResponseEntity<List<User>> response = userController.listUsers();

        assertEquals(2, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getName());
        assertEquals("Bob", response.getBody().get(1).getName());
    }

    @Test
    void testAdminUpdateAndDeleteFlow() {

        User admin = new User("Admin", "admin@example.com");
        admin.setPassword("adminpw");
        admin.setAdmin(true);
        userController.addUser(admin);

        User normal = new User("Normal", "normal@example.com");
        normal.setPassword("normalpw");
        userController.addUser(normal);

        User updated = new User("Normal Updated", "normal@example.com");
        updated.setPassword("newpw");
        updated.setAdmin(false);

        ResponseEntity<?> updateResponse = userController.updateUser(
                normal.getId(),
                "admin@example.com",
                "adminpw",
                updated
        );

        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        User updatedBody = (User) updateResponse.getBody();
        assertEquals("Normal Updated", updatedBody.getName());


        ResponseEntity<?> deleteResponse = userController.deleteUser(
                normal.getId(),
                "admin@example.com",
                "adminpw"
        );

        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
    }
}
