package ie.atu.ci_cd_library_system;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping

public class MenuController {

    @GetMapping("/Buy")
    public String buy()
    {
        return "Purchase";
    }

    @GetMapping("/Rent")
    public String rent()
    {
        return "Rent";
    }

    @GetMapping("/Returning")
    public String returning()
    {
        return "Return";
    }

    @GetMapping("/Menu")
    public Menu menu(@RequestParam String buy, @RequestParam String rent, @RequestParam String returning, @RequestParam String choice)
    {
        String userChoice = "";

        switch (choice)
        {
            case "1":
                userChoice = buy;
                break;

            case "2":
                userChoice = rent;
                break;

            case "3":
                userChoice = returning;
                break;

            default:

                break;
        }
        return new Menu(choice, userChoice);
    }
}
