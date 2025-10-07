package ie.atu.ci_cd_library_system;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequestMapping("/api")
@RestController
public class LoginController {
    //hardcoded list of admin users no permissions yet just a login
    private final List<Login> users= Arrays.asList(
        new Login("patrick","password") ,
        new Login("cathal","password")   ,
        new Login("fionn","password")  ,
        new Login("enzo","password")
    );

    @PostMapping("/login")
    public LoginResponse login(@RequestBody Login login){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getPassword().equals(login.getPassword() )&&users.get(i).getUsername().equalsIgnoreCase(login.getUsername())){
                return new LoginResponse("Login successful Welcome, " + users.get(i).getUsername());
            }
        }
        return new LoginResponse("Login unsuccessful Invalid Credentials") ;
    }

}

