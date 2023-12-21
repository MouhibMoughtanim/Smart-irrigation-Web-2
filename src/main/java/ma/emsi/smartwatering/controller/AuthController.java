package ma.emsi.smartwatering.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.service.AppUserService;

@Controller
@RequestMapping("/")
public class AuthController {

	// Login form
	@Autowired
	AppUserService userService;

	@GetMapping("")
	public RedirectView redirectUser() {
		System.out.println("Redirect");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		System.out.println(authentication);
		boolean admin = authentication.getAuthorities().stream()
				.anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
		if(admin)
			return new RedirectView("/users/list");
		else
			return new RedirectView("/farmer");
	}



	@GetMapping("login")
	public String login() {
		return "login.html";
	}


	@GetMapping("inscription")
	public String register() {
		return "register.html";
	}

	@PostMapping("register")
	public RedirectView AddUser(@RequestParam("username") String username, @RequestParam("password") String password,
								@RequestParam("confirmation") String confirmation ) {

		if(password.equals(confirmation)) {
			AppUser newUser = new AppUser();
			newUser.setPassword(password);
			newUser.setUsername(username);
			newUser.setRole("USER");
			userService.saveUser(newUser);

		}

		return new RedirectView("/login");
	}


}
