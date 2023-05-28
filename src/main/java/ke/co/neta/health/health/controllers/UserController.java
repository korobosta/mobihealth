package ke.co.neta.health.health.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ke.co.neta.health.health.controllers.auth.CustomUserDetailsService;
import ke.co.neta.health.health.models.User;
import ke.co.neta.health.health.repositories.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;

    private CustomUserDetailsService userService;

    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String registerPage(){
        return "index";
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);
        
        return "users";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user,BindingResult result,Model model) {

        User existingUser = null;

        existingUser = userService.getUserByEmail(user.getEmail());
        if(existingUser != null){
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        existingUser = userService.getUserByPhoneNumber(user.getPhoneNumber());
        if(existingUser != null){
            result.rejectValue("phoneNumber", null,
                    "There is already an account registered with the same phone number");
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            return "signup_form";
        }
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        userRepo.save(user);
        
        return "register_success";
    }
    
}
