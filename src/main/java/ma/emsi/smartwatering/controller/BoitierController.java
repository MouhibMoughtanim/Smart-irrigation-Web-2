package ma.emsi.smartwatering.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import ma.emsi.smartwatering.model.Boitier;
import ma.emsi.smartwatering.model.Capteur;
import ma.emsi.smartwatering.model.Connection;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.service.BoitierService;
import ma.emsi.smartwatering.service.CapteurService;
import ma.emsi.smartwatering.service.ConnectionService;

@Controller
@RequestMapping("/boitiers")
public class BoitierController {
	
	@Autowired
	private BoitierService boitierService;
	@Autowired
	private CapteurService capteurService;
	@Autowired
	private ConnectionService connectionService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	
	
	
	@GetMapping("")
	public String login(Model model) {	
		List<Boitier> boitiers = boitierService.getBoitiers();
		
		model.addAttribute("boitiers", boitiers);
			
		return "boitiers.html";
	}
	
	@GetMapping("/update/{id}")
	public String updateForm(Model model,@PathVariable long id) {	
		List<Boitier> boitiers = boitierService.getBoitiers();
		
		model.addAttribute("boitiers", boitiers);
		Boitier updateBoitier = boitierService.getBoitier(id);
		model.addAttribute("updateBoitier", updateBoitier);
		
		return "boitiers.html";
	}
	
	
	@PostMapping("/update/{id}")
	public RedirectView update(Model model,@PathVariable long id, @RequestParam("ref") String ref, @RequestParam("code") String code,
			@RequestParam("type") String type, @RequestParam("image") MultipartFile file) {	
		
		Boitier boitier = boitierService.getBoitier(id);
		if(!ref.isEmpty())
			boitier.setRef(ref);
		if(!code.isEmpty())
			boitier.setCode(code);
		if(!type.isEmpty())
			boitier.setType(type);
		if(!file.isEmpty()) {
			StringBuilder fileNames = new StringBuilder();
			String imagePath =  "boitier_" + boitier.getRef().replace(" ", "_") + "_" + file.getOriginalFilename();
			
			
			Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
			fileNames.append(file.getOriginalFilename()+ "");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			boitier.setImage("/uploads/" +  imagePath);
		}
		
		boitierService.saveBoitier(boitier);
		
		return new RedirectView("/boitiers");
	}
	

	@PostMapping("")
	public RedirectView addBoitier(@RequestParam("ref") String ref, @RequestParam("type") String type, @RequestParam("code") String code, @RequestParam("image") MultipartFile file) {
		
		
		if(ref.isEmpty() || type.isEmpty() || code.isEmpty())
			return new RedirectView("/boitiers");
		
		Boitier newBoitier = new Boitier();
		
		
		StringBuilder fileNames = new StringBuilder();
		String imagePath =  "boitier_" + ref.replace(" ", "_") + "_" + file.getOriginalFilename();
		
		
		Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
		fileNames.append(file.getOriginalFilename()+ "");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		newBoitier.setImage("/uploads/" +  imagePath);
		
		newBoitier.setCode(code);
		newBoitier.setRef(ref);
		newBoitier.setType(type);
		boitierService.saveBoitier(newBoitier);
		
		
		return new RedirectView("/boitiers");
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable long id) {
		
		try {
			boitierService.supprimer(id);
		} catch (Exception e) {
			
		}
		
		return new RedirectView("/boitiers");
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable long id, Model model) {
		
		Boitier boitier = boitierService.getBoitier(id);
		List<Capteur> capteurs = capteurService.getCapteurs();
		
		model.addAttribute("boitier", boitier);
		model.addAttribute("capteurs", capteurs);
		
		return "boitierDetails.html";
	}
	
	@PostMapping("/{id}/capteur")
	public RedirectView capteur(@PathVariable("id") long id, @RequestParam("capteur_id") long capteur_id, @RequestParam("branche") String branche, @RequestParam("fonctionnel") boolean fonctionnel) {
		
		Boitier boitier = boitierService.getBoitier(id);
		Capteur capteur = capteurService.getCapteur(capteur_id);
		
		
		
		Connection connection = new Connection();
		connection.setCapteur(capteur);
		connection.setBranche(branche);
		connection.setFonctionnel(fonctionnel);
		connection = connectionService.saveConnection(connection);
		
		boitier.getConnections().add(connection);
		
		boitierService.saveBoitier(boitier);
		
		return new RedirectView("/boitiers/details/" + id);
	}
	
	@GetMapping("/{boitier_id}/on/{id}")
	public RedirectView turnOn(@PathVariable("id") long id, @PathVariable("boitier_id") long boitier_id) {
		
		Connection connection = connectionService.getConnection(id);
		
		connection.setFonctionnel(true);
		connectionService.saveConnection(connection);
		
		return new RedirectView("/boitiers/details/" + boitier_id);
		
	}
	
	@GetMapping("/{boitier_id}/off/{id}")
	public RedirectView turnOff(@PathVariable("id") long id, @PathVariable("boitier_id") long boitier_id) {
		
		Connection connection = connectionService.getConnection(id);
		
		connection.setFonctionnel(false);
		connectionService.saveConnection(connection);
		
		return new RedirectView("/boitiers/details/" + boitier_id);
		
	}
	


}
