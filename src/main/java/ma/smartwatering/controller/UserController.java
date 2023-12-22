package ma.smartwatering.controller;

import java.util.HashMap;
import java.util.List;

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
import ma.smartwatering.model.EspaceVert;
import ma.smartwatering.service.AppUserService;
import ma.smartwatering.service.EspaceVertService;


@Controller
@RequestMapping("/users")
public class UserController {


	private final AppUserService userService;

	private final EspaceVertService espaceService;
	@Autowired
	public UserController(AppUserService userService, EspaceVertService espaceService) {
		this.userService = userService;
		this.espaceService = espaceService;
	}

	@GetMapping("")
	public String newUser() {	
		return "newUser.html";
	}
	
	@PostMapping("")
	public RedirectView addUser(@RequestParam("username") String username, @RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("role") String role, @RequestParam("password") String password, @RequestParam("confirmation") String confirmation){
		
		if(!role.isEmpty() && !password.isEmpty()  && password.equals(confirmation)) {
			AppUser newUser = new AppUser();
			newUser.setAddress(address);
			newUser.setPassword(password);
			newUser.setRole(role);
			newUser.setPhone(phone);
			newUser.setUsername(username);
			System.out.println(newUser);
			userService.saveUser(newUser);
			return new RedirectView("/users/list");
		}
		return new RedirectView("/users");
	}
	
	@GetMapping("/details/{id}")
	public String userDetails(@PathVariable long id,Model model) {	
		AppUser user = userService.getUser(id);
	    model.addAttribute("user", user);
		return "userDetails.html";
	}

	@GetMapping("/list")
	public String usersList(Model model) {	
		List<AppUser> users = userService.getUsers();
		System.out.println(users);
	    model.addAttribute("users", users);
		return "userslist.html";
	}
	
	@GetMapping("/update/{id}")
	public String updateUser(@PathVariable long id, Model model) {
		AppUser user = userService.getUser(id);
	    model.addAttribute("user", user);
		return "updateUser.html";
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView supprimer(@PathVariable long id) {
		System.out.print("Delete user : id = " + id);
		userService.supprimer(id);
		return new RedirectView("/users/list");
	}
	
	@GetMapping("/espaces")
	public String userEspaces(Model model) {
		List<AppUser> users = userService.getUsers();
	    
		model.addAttribute("users", users);
	    HashMap<EspaceVert, AppUser> usersEspaces = new HashMap<EspaceVert, AppUser>();
	    model.addAttribute("usersEspaces", usersEspaces);
	    
	    for(AppUser user: users) {
	    	for(EspaceVert espace : user.getEspacesVerts()) {
	    		usersEspaces.put(espace, user);
	    	}
	    }
	    
	    List<EspaceVert> espaces = espaceService.getNonAssignedSpaces();
	    model.addAttribute("espaces", espaces);
	    
	    
		return "userEspace.html";
	}
	
	@PostMapping("/espaces")
	public RedirectView userEspaces(@RequestParam("user_id") long user_id, @RequestParam("espace_id") long espace_id) {
		
		AppUser user = userService.getUser(user_id);
		EspaceVert espace = espaceService.get(espace_id);
		
		System.out.println(user);
		System.out.println(espace);
		user.getEspacesVerts().add(espace);
		System.out.println(user.getEspacesVerts());
		userService.saveUser(user);
		
		return new RedirectView("/users/espaces");
		
	}
	
	@GetMapping("/{user_id}/espace/{espace_id}")
	public RedirectView freeEspace(@PathVariable("user_id") long user_id, @PathVariable("espace_id") long espace_id) {
		
		AppUser user = userService.getUser(user_id);
		EspaceVert espace = espaceService.get(espace_id);
		
		System.out.println(user);
		System.out.println(espace);
		user.getEspacesVerts().remove(espace);
		System.out.println(user.getEspacesVerts());
		userService.saveUser(user);
		
		return new RedirectView("/users/espaces");
		
	}
	
	@PostMapping("/update/{id}")
	public RedirectView modifier(@PathVariable long id, @RequestParam("username") String username, @RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("role") String role, @RequestParam("password") String password, @RequestParam("confirmation") String confirmation) {

		AppUser user = userService.getUser(id);
		if(!password.isEmpty()  && password.equals(confirmation))
			user.setPassword(password);
		if(!address.isEmpty())
			user.setAddress(address);
		if(!role.isEmpty())
			user.setRole(role);
		if(!username.isEmpty())
			user.setUsername(username);
		if(!phone.isEmpty())
			user.setPhone(phone);
			userService.saveUser(user);

            if(user.getRole() == "ADMIN") {
                return new RedirectView("/users/details/" + id);
            }
        if(user.getRole() == "USER") {
            return new RedirectView("/farmer");
        }
        return new RedirectView("/");
	}
	
	
	
	
	
}
