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
        // Ensure quantity is at least 0
        if (book.getQuantity() < 0) {
            book.setQuantity(0);
        }
        book.setMaxQuantity(book.getQuantity());
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Books> getBooks() {
        return bookRepository.findAll();
    }
}
