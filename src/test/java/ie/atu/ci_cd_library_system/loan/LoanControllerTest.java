package ie.atu.ci_cd_library_system.loan;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoanController.class)
@Import(LoanControllerTest.LoanServiceTestConfig.class)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoanService loanService;

    @Test
    void testCreateLoan() throws Exception {
        Loan loan = new Loan(1L, 1L, 1L, null, false);
        Mockito.when(loanService.createLoan(1L, 1L)).thenReturn(loan);

        mockMvc.perform(post("/loans")
                        .param("userId", "1")
                        .param("bookId", "1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @TestConfiguration
    static class LoanServiceTestConfig {
        @Bean
        public LoanService loanService() {
            return Mockito.mock(LoanService.class);
        }
    }
}
