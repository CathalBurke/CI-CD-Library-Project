package ie.atu.ci_cd_library_system.book;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookHandling {

    private final BookRepository bookRepository;

    // POST /books  → save book to DB
    @PostMapping
    public Books addBook(@RequestBody Books book) {
        return bookRepository.save(book);
    }

    // GET /books  → get all books from DB
    @GetMapping
    public List<Books> getBooks() {
        return bookRepository.findAll();
    }
}