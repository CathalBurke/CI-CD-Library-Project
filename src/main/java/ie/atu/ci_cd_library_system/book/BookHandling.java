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
        return bookRepository.save(book);
    }

    @GetMapping
    public List<Books> getBooks() {
        return bookRepository.findAll();
    }
}
