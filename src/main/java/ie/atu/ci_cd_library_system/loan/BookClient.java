package ie.atu.ci_cd_library_system.loan;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "book-service", url = "${book.service.url}")
public interface BookClient {

    @PostMapping("/books/{id}/reserve")
    Boolean reserveBook(@PathVariable("id") Long id);

    @PostMapping("/books/{id}/return")
    Boolean returnBook(@PathVariable("id") Long id);
}
