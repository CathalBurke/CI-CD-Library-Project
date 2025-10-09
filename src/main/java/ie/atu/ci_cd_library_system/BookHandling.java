package ie.atu.ci_cd_library_system;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping

public class BookHandling {
    private List<Books> book = new ArrayList<>();

    public void buyBook(String title) {
    }

    public void rentBook(String title) {
    }

    public void returnBook(String title) {
    }
}
