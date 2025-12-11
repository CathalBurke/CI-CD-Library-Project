package ie.atu.ci_cd_library_system.loan;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestParam Long userId, @RequestParam Long bookId) {
        return ResponseEntity.status(201).body(loanService.createLoan(userId, bookId));
    }

    @GetMapping("/user/{userId}")
    public List<Loan> getLoansForUser(@PathVariable Long userId) {
        return loanService.getLoansForUser(userId);
    }

    @PutMapping("/{loanId}/return")
    public Loan returnLoan(@PathVariable Long loanId) {
        return loanService.returnLoan(loanId);
    }
}
