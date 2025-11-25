package ie.atu.ci_cd_library_system.menu;


import ie.atu.ci_cd_library_system.book.BookService;
import ie.atu.ci_cd_library_system.book.Books;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class MenuController {

    private final BookService bookService;

    public MenuController(BookService bookService) {
        this.bookService = bookService;
    }


    @PostMapping("/buy/{id}")
    public Books buy(@PathVariable Long id) {
        return bookService.buyBook(id);
    }

    @PostMapping("/rent{id}")
    public Books rent(@PathVariable Long id) {
        return bookService.rentBook(id);
    }

    @PostMapping("/return{id}")
    public Books returning(@PathVariable Long id) {
        return bookService.returnBook(id);
    }

    @GetMapping("/Menu")
    public Menu menu(@RequestParam String buy,
                     @RequestParam String rent,
                     @RequestParam String returning,
                     @RequestParam String choice) {

        String userChoice = "";

        switch (choice) {
            case "1" -> userChoice = buy;
            case "2" -> userChoice = rent;
            case "3" -> userChoice = returning;
            default -> {}
        }


        return new Menu(choice, userChoice);
    }
}
