package ie.atu.ci_cd_library_system.book;

import ie.atu.ci_cd_library_system.menu.Payment;
import ie.atu.ci_cd_library_system.menu.PaymentService;
import ie.atu.ci_cd_library_system.user.User;
import ie.atu.ci_cd_library_system.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    public BookService(BookRepository bookRepository, UserRepository userRepository, PaymentService paymentService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.paymentService = paymentService;
    }
    //transactional makes sure either all of it or nothing goes to database
    @Transactional
    public Books buyBook(String title, double userPayment) {
        Books book = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Title not found"));

        double price = book.getPrice();

        Payment payment = paymentService.processPayment(title, userPayment, price);

        if (!payment.isSuccess()) {
            throw new RuntimeException(payment.getMessage());
        }

        if (book.getQuantity() > 0 && book.getMaxQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            book.setMaxQuantity(book.getMaxQuantity() -1);
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book out of stock");
        }
    }

    @Transactional
    public Books rentBook(String title, Long userId) {
        Books book = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        int rentMax = 3;

        if (user.getRentCount() >= rentMax) {
            throw new RuntimeException("You reached your rental limit of " + rentMax + " books.");
        }

        if (book.getQuantity() > 0) {
            book.setQuantity(book.getQuantity() - 1);
            user.setRentCount(user.getRentCount() + 1);
            userRepository.save(user);
            return bookRepository.save(book);
        } else {
            throw new RuntimeException("Book not available for rent");
        }
    }

    @Transactional
    public Books returnBook(String title, Long userId) {

        Books book  = bookRepository.findByTitleIgnoreCase(title)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(book.getQuantity() >= book.getMaxQuantity()){
            throw new RuntimeException("All books have been returned");
        }
        book.setQuantity(book.getQuantity() + 1);
        user.setRentCount(user.getRentCount() - 1);
        userRepository.save(user);
        return bookRepository.save(book);
    }
}
