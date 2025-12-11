package ie.atu.ci_cd_library_system.menu;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @PostMapping("/pay")
    public Payment pay(@RequestParam String title, @RequestParam double price, @RequestParam double userPayment) {
        return service.processPayment(title, price, userPayment);
    }
}
