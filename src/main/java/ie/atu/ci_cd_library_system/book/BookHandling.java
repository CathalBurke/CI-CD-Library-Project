package ie.atu.ci_cd_library_system.book;


import ie.atu.ci_cd_library_system.user.User;
import ie.atu.ci_cd_library_system.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookHandling {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookHandling(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private boolean isNotAdmin(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(User::isAdmin)
                .filter(user -> user.getPassword().equals(password))
                .isEmpty();
    }

    @PostMapping
    public Books addBook(@RequestBody Books book) {
        //makes sure theres no books with same title but just different case
        bookRepository.findByTitleIgnoreCase(book.getTitle()).ifPresent(existingBook -> {
            throw new RuntimeException("Book with title " + book.getTitle() + " already exists");
        });


        // Ensure quantity is a posiitive number above 1
        if (book.getQuantity() <= 0) {
            throw new RuntimeException("Quantity cannot be at or below 0");
        }
        book.setQuantity(book.getQuantity());
        book.setMaxQuantity(book.getQuantity());
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Books> getBooks() {
        return bookRepository.findAll();
    }

    // Admin-only: Add stock to a book by title
    @PutMapping("/add-stock/{title}")
    public ResponseEntity<?> addStock(
            @PathVariable String title,
            @RequestParam int quantity,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword
    ) {
        if (isNotAdmin(adminEmail, adminPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only admin users can add stock");
        }

        if (quantity <= 0) {
            return ResponseEntity.badRequest()
                    .body("Quantity must be greater than 0");
        }

        return bookRepository.findByTitleIgnoreCase(title)
                .map(book -> {
                    book.setQuantity(book.getQuantity() + quantity);
                    book.setMaxQuantity(book.getMaxQuantity() + quantity);
                    Books updatedBook = bookRepository.save(book);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin-only: Remove stock from a book by title
    @PutMapping("/remove-stock/{title}")
    public ResponseEntity<?> removeStock(
            @PathVariable String title,
            @RequestParam int quantity,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword
    ) {
        if (isNotAdmin(adminEmail, adminPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only admin users can remove stock");
        }

        if (quantity <= 0) {
            return ResponseEntity.badRequest()
                    .body("Quantity must be greater than 0");
        }

        return bookRepository.findByTitleIgnoreCase(title)
                .map(book -> {
                    int newQuantity = book.getQuantity() - quantity;
                    int newMaxQuantity = book.getMaxQuantity() - quantity;

                    if (newQuantity < 0 || newMaxQuantity < 0) {
                        return ResponseEntity.badRequest()
                                .body("Cannot remove more stock than available");
                    }

                    book.setQuantity(newQuantity);
                    book.setMaxQuantity(newMaxQuantity);
                    Books updatedBook = bookRepository.save(book);
                    return ResponseEntity.ok(updatedBook);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Admin-only: Delete a book by title
    @DeleteMapping("/delete/{title}")
    public ResponseEntity<?> deleteBook(
            @PathVariable String title,
            @RequestParam String adminEmail,
            @RequestParam String adminPassword
    ) {
        if (isNotAdmin(adminEmail, adminPassword)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Only admin users can delete books");
        }

        return bookRepository.findByTitleIgnoreCase(title)
                .map(book -> {
                    bookRepository.delete(book);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
