package ma.emsi.smartwatering.api;

import ma.smartwatering.model.AppUser;
import ma.smartwatering.model.LoginResponse;
import ma.smartwatering.repository.AppUserRepository;
import ma.smartwatering.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class loginController {
    private AppUserService userService;
    @Autowired
    private AppUserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String,String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        // Your authentication logic here
        // Authenticate the user based on the provided username and password
        System.out.println(loginRequest);

        AppUser user = userRepo.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            // Authentication successful
            return ResponseEntity.ok(user);
        } else {
            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

    }

    // Implement your access token generation logic here
    private String generateAccessToken() {
        // Generate and return the access token
        // You might want to use JWT or another secure mechanism for generating access tokens
        return "ss";
    }
    @GetMapping("/details/{id}")
    public String userDetails(@PathVariable long id, Model model) {
        AppUser user = userService.getUser(id);
        model.addAttribute("user", user);
        return "sssalmaa"+user;
    }




    }

    // You can create a LoginRequest class to represent the login payload
    // For example, a simple class with username and password fields:


