package ie.atu.ci_cd_library_system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class LoginController {
    @PostMapping("/login")
    public String login(@RequestBody Login login){
        String hardcodeUsername ="patrick";
        String hardcodePassword ="password";

        if(login.getUsername().equals(hardcodeUsername) && login.getPassword().equals(hardcodePassword)){
            return "Login successful Welcome,"+login.getUsername() ;
        }
        else{
            return "Invalid Username or Password";
        }
    }

}

