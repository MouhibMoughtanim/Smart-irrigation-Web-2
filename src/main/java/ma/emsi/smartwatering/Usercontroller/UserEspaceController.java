package ma.emsi.smartwatering.Usercontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Blob;
import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.EspaceVertService;

@Controller
@RequestMapping("/farmer/espaces")
public class UserEspaceController {

	@Autowired
	EspaceVertService espaceService;
	@Autowired
	AppUserService userService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	
	
	@GetMapping("/new")
	public String newEspace() {	
		return "newEspace.html";
	}
	
	@PostMapping("/new")
	public RedirectView addEspace(@RequestParam("libelle") String libelle, @RequestParam("image") MultipartFile file) {
		StringBuilder fileNames = new StringBuilder();
		String imagePath =  "esapce_" + libelle.replace(" ", "_") + "_" + file.getOriginalFilename();
		if(libelle.isEmpty())
			return new RedirectView("/espaces/new");
		
		Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
		fileNames.append(file.getOriginalFilename()+ "");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		EspaceVert newEspace = new EspaceVert();
		newEspace.setImage("/uploads/" +  imagePath);
		newEspace.setLibelle(libelle);
		
		newEspace = espaceService.saveEspaceVert(newEspace);
		AppUser user = userService.currentUser();
		
		user.getEspacesVerts().add(newEspace);
		userService.saveUser1(user);
		
		
		return new RedirectView("/farmer");
	}
	
	
	@PostMapping("/delete/{id}")
	public RedirectView supprimer(@PathVariable long id) {
		
		
		AppUser user = userService.currentUser();
		if(user.getEspacesVerts().contains(espaceService.get(id)))
			espaceService.supprimer(id);
		
		return new RedirectView("/farmer");
	}
	
	@GetMapping("/update/{id}")
	public String updateUser(@PathVariable long id, Model model) {
		EspaceVert espace = espaceService.get(id);
		
		
		
	    model.addAttribute("espace", espace);
		return "updateEspace.html";
	}
	
	@PostMapping("/update/{id}")
	public RedirectView modifier(@PathVariable long id, @RequestParam("libelle") String libelle, @RequestParam("image") MultipartFile file) {
		
		EspaceVert espace = espaceService.get(id);
		
		AppUser user = userService.currentUser();
		if(!user.getEspacesVerts().contains(espace))
			return new RedirectView("/farmer");
		
		
		
		if(!file.isEmpty()) {
			StringBuilder fileNames = new StringBuilder();
			String imagePath =  "esapce_" + libelle.replace(" ", "_") + "_" + file.getOriginalFilename();
			if(libelle.isEmpty())
				return new RedirectView("/espaces/new");
			
			Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
			fileNames.append(file.getOriginalFilename()+ "");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			espace.setImage("/uploads/" +  imagePath);
			
		}
			
		if(!libelle.isEmpty())
			espace.setLibelle(libelle);
			
		espaceService.saveEspaceVert(espace);
		return new RedirectView("/farmer");

	}
	

}
