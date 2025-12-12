package ie.atu.ci_cd_library_system.loan;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class LoanService {

    /private final LoanRepository loanRepository;
    private final UserClient userClient;
    private final BookClient bookClient;

    public LoanService(LoanRepository loanRepository, UserClient userClient, BookClient bookClient) {
        this.loanRepository = loanRepository;
        this.userClient = userClient;
        this.bookClient = bookClient;
    }

    public Loan createLoan(Long userId, Long bookId) {
        if (!userClient.userExists(userId)) {
            throw new RuntimeException("User not found: " + userId);
        }
        if (!bookClient.reserveBook(bookId)) {
            throw new RuntimeException("Book Not available: " + bookId);
        }
        Loan loan = new Loan(null, userId, bookId, LocalDate.now(), LocalDate.now().plusWeeks(2), false);
        return loanRepository.save(loan);
    }

    public List<Loan> getLoansForUser(Long userId) {
        return loanRepository.findByUserId(userId);
    }
    public Loan returnLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
        if (!loan.isReturned()) {
            bookClient.returnBook(loan.getBookId());
            loan.setReturned(true);
            loanRepository.save(loan);
        }
        return loan;
    }
}
