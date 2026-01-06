package ie.atu.ci_cd_library_system.loan;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${user.service.url}")
public interface UserClient {


    @GetMapping("/users/{id}/exists")
    Boolean userExists(@PathVariable("id") Long id);

}
