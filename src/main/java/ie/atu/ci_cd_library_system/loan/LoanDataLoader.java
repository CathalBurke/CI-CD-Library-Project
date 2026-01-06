package ie.atu.ci_cd_library_system.loan;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LoanDataLoader implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(LoanDataLoader.class);
    private final LoanRepository loanRepository;

    public LoanDataLoader(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    @Override
    public void run(String... args) {
        logger.info("Seeding sample loans");
        Loan sample = new Loan(null, 1L, 1L, LocalDate.now().minusDays(3), LocalDate.now().plusDays(11), false);
        loanRepository.save(sample);
        logger.info("Seeding completed");
    }
}
