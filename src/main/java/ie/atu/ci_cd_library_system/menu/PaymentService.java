package ie.atu.ci_cd_library_system.menu;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment processPayment(String title, double userPayment, double requiredPrice) {

        Payment payment;

        if (userPayment < requiredPrice) {
            payment = new Payment(null, title, userPayment, false, "Insufficient payment. Price is " + requiredPrice);
        }

        else {
            payment = new Payment(null, title, userPayment, true, "Payment successful");
        }

        return repository.save(payment);
    }
}