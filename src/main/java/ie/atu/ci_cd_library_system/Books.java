package ie.atu.ci_cd_library_system;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class Books {
    private String title;
    private String author;
    private String genre;
}
