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

            AppUser user = userRepo.findByUsername(username);

            System.out.println(user);
            System.out.println(user.getId());
            System.out.println(user.getRole());

            if (user != null && passwordEncoder.matches(password, user.getPassword())) {

                if ("USER".equals(user.getRole())) {
                    System.out.println(user.getId());
                    List<EspaceVert> ss=getAllEspacesVerts();
                    System.out.println(ss);
                    reponse loginResponse = new reponse();
                    loginResponse.setUser(user);
                    loginResponse.setEspacesVerts(ss);
                    System.out.println(loginResponse);
                    return ResponseEntity.ok(loginResponse);
                } else if ("ADMIN".equals(user.getRole())) {
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

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    private List<EspaceVert> getAllEspacesVerts() {

        return espaceService.getEspacesVerts();
    }



    @GetMapping("/details/{id}")
    public String userDetails(@PathVariable long id, Model model) {
        AppUser user = userService.getUser(id);
        model.addAttribute("user", user);
        return "sssalmaa"+user;
    }




    }

