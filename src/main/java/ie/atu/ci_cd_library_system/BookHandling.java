package ie.atu.ci_cd_library_system;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/Books")

public class BookHandling {

    private List<Books> books = new ArrayList<>();

    @GetMapping("/getBooks")
    public List<Books> getBooks()
    {
        Books myBooks = new Books("book1", "JohnDoe","Fantasy");
        return books;
    }

    @PostMapping("/addBooks")
    public Books addBooks(@Valid @RequestBody Books myBooks)
    {
        books.add(myBooks);
        return myBooks;
    }

    @GetMapping("/Count")
    public int bookCount()
    {
        return books.size();
    }


}
