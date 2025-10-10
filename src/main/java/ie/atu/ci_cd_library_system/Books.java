package ie.atu.ci_cd_library_system;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Books {
    @NotBlank  @Size(min=2, max=10)
    private String title;
    private String author;
    private String genre;
}
