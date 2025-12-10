package ie.atu.ci_cd_library_system.book;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")

public class BookHandling {

    private final BookRepository bookRepository;

    public BookHandling(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}
