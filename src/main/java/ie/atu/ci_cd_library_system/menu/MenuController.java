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

    @PostMapping("/buy/{title}")
    public Books buy(@PathVariable String title,  @RequestParam double payment) {
        return bookService.buyBook(title, payment);
    }

    @PostMapping("/rent/{title}")
    public Books rent(@PathVariable String title, @RequestParam Long userId) {
        return bookService.rentBook(title, userId);
    }

    @PostMapping("/return{title}")
    public Books returning(@PathVariable String title, @RequestParam Long userId) {
        return bookService.returnBook(title, userId);
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
