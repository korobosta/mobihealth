package ke.co.neta.health.health.controllers.auth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ke.co.neta.health.health.models.Feedback;
import ke.co.neta.health.health.models.Role;
import ke.co.neta.health.health.models.User;
import ke.co.neta.health.health.repositories.RoleRepository;
import ke.co.neta.health.health.repositories.UserRepository;
 
@Service
public class CustomUserDetailsService implements UserDetailsService {
 
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepository;
     
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new CustomUserDetails(user);
    }

    public User getUserByEmail(String email) {
        User user = userRepo.findByEmail(email);
        return user;
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        User user = userRepo.findByPhoneNumber(phoneNumber);
        return user;
    }

    public Feedback  createUser(User user){
        User existingUser = null;

        boolean success = false;

        Feedback feedback= new Feedback();

        List<Map<String, String>> errors = new ArrayList<>();

        existingUser = getUserByEmail(user.getEmail());
        if(existingUser != null){
            String error = "There is already an account registered with the same email address";
            Map<String, String> emailMap = new HashMap<>();
            emailMap.put("email", error);
            errors.add(emailMap);
        }

        existingUser = getUserByPhoneNumber(user.getPhoneNumber());
        if(existingUser != null){
            String error = "There is already an account registered with the same phone number";
            Map<String, String> emailMap = new HashMap<>();
            emailMap.put("phoneNumber", error);
            errors.add(emailMap);
        }

        if (errors.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            String error = "";

            try {
                userRepo.save(user);
                assignRoleToUser(user.getId(),user.getRoleId());
                success = true;
            } catch (DataIntegrityViolationException e) {
                // Handle database constraint violations
                error = "Failed to save user: Data integrity violation.";
            } catch (JpaSystemException e) {
                // Handle JPA system exceptions
                error =  "Failed to save user: JPA system error.";
            } catch (Exception e) {
                // Handle all other exceptions
                error =  "Failed to save user: " + e.getMessage();
            }

            if(error != ""){
                Map<String, String> errorMap = new HashMap<>();
                errorMap.put("error", error);
                errors.add(errorMap);
            }
        }

        feedback.setErrors(errors);
        feedback.setSuccess(success);

        return feedback;
    }

    public String assignRoleToUser(Long userId, Long roleId) {
        try {
            User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
            user.addRole(role);
            userRepo.save(user);
            return "Role assigned successfully.";
        } catch (Exception e) {
            return "Failed to assign role: " + e.getMessage();
        }
    }
 
}