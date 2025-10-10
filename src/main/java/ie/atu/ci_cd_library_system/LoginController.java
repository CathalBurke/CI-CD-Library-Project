package ie.atu.ci_cd_library_system;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/api")
@RestController
public class LoginController {
    //hardcoded list of admin users no permissions yet just a login
    private final List<Login> users=new ArrayList<>(Arrays.asList(
        new Login("patrick","password") ,
        new Login("cathal","password")   ,
        new Login("fionn","password")  ,
        new Login("enzo","password")
    ));
    /*@GetMapping("/add")
    public  LoginResponse addUser(@RequestParam String username, @RequestParam String password){
        users.add

    }*/

    private Login findByLogin(String login){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUsername().equals(login)){
                return users.get(i);
            }
        }
        return null;
    }


        @PostMapping("/users")//create
        public LoginResponse createUser(@RequestBody Login Newuser){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUsername().equals(Newuser.getUsername())){
                return new LoginResponse("User already exists!");
            }
        }
        users.add(Newuser);
        return new LoginResponse("User has been created! Welcome "+Newuser.getUsername());
        }
    @GetMapping("/list")//read
    public List<Login> getUsers(){
        return users;
    }

   /* @PutMapping("/Update/[{username}")
    public String updateUser(@PathVariable String username, @RequestBody Login Updated){
        User existing = findByLogin(username);
        existing.setName(username);

    }*/
    @PostMapping("/login")//creatwe
    public LoginResponse login(@RequestBody Login login){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getPassword().equals(login.getPassword() )&&users.get(i).getUsername().equalsIgnoreCase(login.getUsername())){
                return new LoginResponse("Login successful Welcome, " + users.get(i).getUsername());
            }
        }
        return new LoginResponse("Login unsuccessful Invalid Credentials") ;
    }

    /*@DeleteMapping("/delete")
    public String deleteUser(@PathVariable String login){
        for(int i=0;i<users.size();i++){
            if(users.get(i).getUsername().equalsIgnoreCase(login.getUsername())){
                users.remove(i);
                return new LoginResponse("Deleted user, " + users.get(i).getUsername());
            }
        }
        return new LoginResponse("Delete unsuccessful Invalid Credentials") ;
    }*/


}

