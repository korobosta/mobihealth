package ke.co.neta.health.health.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import ke.co.neta.health.health.controllers.auth.CustomUserDetailsService;
import ke.co.neta.health.health.controllers.services.RoleService;
import ke.co.neta.health.health.models.Feedback;
import ke.co.neta.health.health.models.User;
import ke.co.neta.health.health.repositories.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleService roleService;

    private CustomUserDetailsService userService;

    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String indexPage(){
        return "index";
    }

    @GetMapping("/api/users")
    @ResponseBody
    public Map<String, Object> getUsers(@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "length", defaultValue = "10") int length) {
        int page = start / length;
        Pageable pageable = PageRequest.of(page, length);
        Page<User> userPage = userRepo.findAll(pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("data", userPage.getContent());
        response.put("recordsTotal", userPage.getTotalElements());
        response.put("recordsFiltered", userPage.getTotalElements());

        return response;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        // List<User> listUsers = userRepo.findAll();
        // model.addAttribute("listUsers", listUsers);
        
        return "users/view_users";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> listUsers = userRepo.findAll();
        long userCount =  userRepo.count();

        model.addAttribute("listUsers", listUsers);
        model.addAttribute("userCount",userCount);
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getRolesByRoleName("Citizen"));
        return "users/register";
    }

    @GetMapping("/users/add")
    public String addUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.getAllRoles());
        return "users/add_user";
    }

    @PostMapping("/users/add")
    public String addUser(User user,BindingResult result,Model model, RedirectAttributes redirectAttributes) {
        Feedback feedback = userService.createUser(user);
        List<Map<String, String>> errors = feedback.getErrors();

        for (Map<String, String> map : errors) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                result.rejectValue(entry.getKey(), null,
                entry.getValue());
            }
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            
            return "users/add_user";
        }
        
        redirectAttributes.addFlashAttribute("message", "Registration was successful");
        
        return "redirect:/users";
    }

    @PostMapping("/process_register")
    public String processRegister(User user,BindingResult result,Model model, RedirectAttributes redirectAttributes) {

        Feedback feedback = userService.createUser(user);
        List<Map<String, String>> errors = feedback.getErrors();

        for (Map<String, String> map : errors) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                result.rejectValue(entry.getKey(), null,
                entry.getValue());
            }
        }

        if(result.hasErrors()){
            model.addAttribute("user", user);
            model.addAttribute("roles", roleService.getRolesByRoleName("Citizen"));
            return "users/register";
        }

        redirectAttributes.addFlashAttribute("message", "Registration was successful");
        
        return "redirect:/login";
    }

    @GetMapping("/users/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        User user = userRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        model.addAttribute("user", user);
        return "users/edit_user";
    }

    @GetMapping("/users/{id}")
    public String viewUser(@PathVariable("id") long id, Model model) {
        User user = userRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        model.addAttribute("user", user);
        return "users/view_user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "users/edit_user";
        }

        User dbUser = userRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        dbUser.setEmail(user.getEmail());
        dbUser.setLastName(user.getLastName());
        dbUser.setFirstName(user.getFirstName());
        dbUser.setPhoneNumber(user.getPhoneNumber());

        String password = user.getPassword();

        if(password != ""){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
        }
        
        userRepo.save(dbUser);
        return "redirect:/users";
    }
        
    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userRepo.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepo.delete(user);
        return "redirect:/users";
    }

    @PostMapping("/{userId}/roles/{roleId}")
    public ResponseEntity<String> assignRoleToUser(@PathVariable Long userId, @PathVariable Long roleId) {
        String result = userService.assignRoleToUser(userId, roleId);
        if (result.equals("Role assigned successfully.")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(500).body(result);
        }
    }
    
}
