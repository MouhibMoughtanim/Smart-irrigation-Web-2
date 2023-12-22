package ma.smartwatering.userController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import ma.smartwatering.model.AppUser;
import ma.smartwatering.service.AppUserService;
import ma.smartwatering.service.EspaceVertService;


@Controller
@RequestMapping("/farmer")
public class ProfilController {


	private final AppUserService userService;

	private final EspaceVertService espaceService;
	@Autowired
	public ProfilController(AppUserService userService, EspaceVertService espaceService) {
		this.userService = userService;
		this.espaceService = espaceService;
	}

	@GetMapping("")
	public String userDetails(Model model) {
		AppUser user = userService.currentUser();
	    model.addAttribute("user", user);
		return "farmerDetails.html";
	}

	
	@GetMapping("/update")
	public String updateUser(Model model) {
		AppUser user = userService.currentUser();
	    model.addAttribute("user", user);
		return "updateUser1.html";
	}

		
	
	@PostMapping("/update")
	public RedirectView modifier(@PathVariable long id, @RequestParam("username") String username, @RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("role") String role, @RequestParam("password") String password, @RequestParam("confirmation") String confirmation) {
		
		AppUser user = userService.currentUser();
		if(!password.isEmpty()  && password.equals(confirmation))
			user.setPassword(password);
		if(!address.isEmpty())
			user.setAddress(address);
		if(!role.isEmpty())
			user.setRole(role);
		if(!username.isEmpty())
			user.setUsername(username);
			
			userService.saveUser(user);
			return new RedirectView("/farmer/details");

	}
	
}
