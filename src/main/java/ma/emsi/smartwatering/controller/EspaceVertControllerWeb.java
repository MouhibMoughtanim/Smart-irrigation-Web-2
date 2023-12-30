package ma.emsi.smartwatering.controller;

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
@RequestMapping("/espaces")
public class EspaceVertControllerWeb {

	@Autowired
	EspaceVertService espaceService;
	@Autowired
	AppUserService userService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	
	@GetMapping("")
	public String list(Model model) {
		List<EspaceVert> assignedEspace = new ArrayList<EspaceVert>();
		List<EspaceVert> non_assignedEspace = new ArrayList<EspaceVert>();
		List<AppUser> users = userService.getUsers();
		
	   
	    
	    for(AppUser user: users) {
	    	for(EspaceVert espace : user.getEspacesVerts()) {
	    		assignedEspace.add(espace);
	    	}
	    }
	    
	   non_assignedEspace = espaceService.getNonAssignedSpaces();
		
	   model.addAttribute("assignedEspace", assignedEspace);
	    model.addAttribute("non_assignedEspace", non_assignedEspace);

		return "espaces.html";
	}
	
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
		
		espaceService.saveEspaceVert(newEspace);
		return new RedirectView("/espaces");
	}
	
	
	@PostMapping("/delete/{id}")
	public RedirectView supprimer(@PathVariable long id) {
		espaceService.supprimer(id);
		return new RedirectView("/espaces");
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
		return new RedirectView("/espaces");

	}
	
	@GetMapping("/responsible/{id}")
	public RedirectView getResponsable(@PathVariable("id") long id) {
		List<AppUser> users = userService.getUsers();
		for(AppUser user : users) {
			for(EspaceVert espace: user.getEspacesVerts()) {
				if(espace.getId() == id) {
					return new RedirectView("/users/details/"+ user.getId());
				}else{
					System.out.println("jj");
				}

			}
		}
		return new RedirectView("/users/espaces");
	}
	@GetMapping("/responsable/{id}")
	public RedirectView getResponsablee(@PathVariable("id") long id) {
		List<AppUser> users = userService.getUsers();
		for(AppUser user : users) {
			for(EspaceVert espace: user.getEspacesVerts()) {
				if(espace.getId() == id) {
					return new RedirectView("/users/details/"+ user.getId());
				}else{
					System.out.println("jj");
				}

			}
		}
		return new RedirectView("/users/espaces");
	}

	
	
	/*
	@GetMapping("/plante")
	public String addPlante() {	
		return "zonePlante.html";
	}
	
	@GetMapping("/{id}/grandeurs")
	public String grandeurs() {	
		return "grandeursZone.html";
	}*/
	
}
