package ie.atu.ci_cd_library_system.loan;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "user_id")
    private Long userId;

    @NotNull
    @Column(name = "book_id")
    private Long bookId;

    @NotNull
    @Column(name = "loan_date")
    private LocalDate loanDate;

    @NotNull
    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "returned")
    private boolean returned;

    public Loan(long id, long userId, long bookId, Object loanDate, boolean returned) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = (LocalDate) loanDate;
        this.returned = returned;
    }}

