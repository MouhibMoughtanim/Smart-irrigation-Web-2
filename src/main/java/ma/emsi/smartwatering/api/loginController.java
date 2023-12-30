package ma.emsi.smartwatering.api;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.model.LoginResponse;
import ma.emsi.smartwatering.repository.AppUserRepository;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.EspaceVertService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class loginController {
    private AppUserService userService;
    @Autowired
    private AppUserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EspaceVertService espaceService;



    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Map<String, String> loginRequest) {
        try {
            String username = loginRequest.get("username");
            String password = loginRequest.get("password");

            System.out.println(loginRequest);

            // Your authentication logic here
            AppUser user = userRepo.findByUsername(username);

            System.out.println(user);
            System.out.println(user.getId());
            System.out.println(user.getRole());

            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
                // Authentication successful

                if ("USER".equals(user.getRole())) {
                    // If the user is a regular user, return user details
                    System.out.println(user.getId());
                    List<EspaceVert> ss=getAllEspacesVerts();
                    System.out.println(ss);
                    reponse loginResponse = new reponse();
                    loginResponse.setUser(user);
                    loginResponse.setEspacesVerts(ss);
                    System.out.println(loginResponse);
                    return ResponseEntity.ok(loginResponse);
                } else if ("ADMIN".equals(user.getRole())) {
                    // If the user is an admin, return user details and all espaces verts
                    System.out.println(user.getId());
                    List<EspaceVert> ss=getAllEspacesVerts();
                    System.out.println(ss);
                    reponse loginResponse = new reponse();
                    loginResponse.setUser(user);
                    loginResponse.setEspacesVerts(ss);
                    System.out.println(loginResponse);
                    return ResponseEntity.ok(loginResponse);
                }
            }

            // Authentication failed
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    private List<EspaceVert> getAllEspacesVerts() {
        // Implement logic to retrieve all espaces verts from the database
        // For example, you can use a repository method to fetch all espaces verts
        return espaceService.getEspacesVerts();
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


