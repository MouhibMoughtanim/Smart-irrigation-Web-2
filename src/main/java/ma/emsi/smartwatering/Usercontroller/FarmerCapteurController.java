package ma.emsi.smartwatering.Usercontroller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ma.emsi.smartwatering.model.*;
import ma.emsi.smartwatering.repository.GrandeurRepository;
import ma.emsi.smartwatering.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.BoitierService;
import ma.emsi.smartwatering.service.CapteurService;
import ma.emsi.smartwatering.service.ConnectionService;

@Controller
@RequestMapping("/farmer/capteurs")
public class FarmerCapteurController {
	
	@Autowired
	private CapteurService capteurService;
	@Autowired
	private AppUserService userService;
	@Autowired
	private BoitierService boiterService;
	@Autowired
	private ConnectionService connectionService;
	@Autowired
	private GrandeurRepository grandeurRepository;
	@Autowired
	private ZoneRepository zoneRepository;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	
	@GetMapping("")
	public String capteurs(Model model) {
		AppUser user = userService.currentUser();
		List<Capteur> capteurs = capteurService.getCapteurByUserId(user.getId());
		List<Boitier> boitiers = boiterService.getBoitierByUserId(user.getId());
		
		model.addAttribute("capteurs", capteurs);
		model.addAttribute("boitiers", boitiers);
			
		return "farmerCapteurs.html";
	}
	
	@GetMapping("/update/{id}")
	public String updateForm(Model model,@PathVariable long id) {	
		AppUser user = userService.currentUser();
		List<Capteur> capteurs = capteurService.getCapteurByUserId(user.getId());
		
		model.addAttribute("capteurs", capteurs);
		Capteur updateCapteur = capteurService.getCapteur(id);
		model.addAttribute("updateCapteur", updateCapteur);
		
		return "farmerCapteurs.html";
	}


	@PostMapping("/receive")
	public ResponseEntity<String> receiveSensorData(@RequestBody Grandeur grandeur) {
		try {
			// Set the current date and time before saving
			grandeur.setDateTime(new Date()); // or LocalDateTime.now() if using Java 8 or later

			// Assuming you have a Zone instance with id 67
			Zone zone = zoneRepository.findById(31L).orElseThrow(() -> new RuntimeException("Zone not found"));

			// Set the Zone on the Grandeur instance
			grandeur.setZone(zone);

			// Assuming GrandeurRepository has a save method
			grandeurRepository.save(grandeur);

			return ResponseEntity.ok("Sensor data received successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body("Error processing sensor data");
		}
	}

/*
@PostMapping("/receive")
public ResponseEntity<String> receiveSensorData(
		@RequestParam("zone_id") long zoneId,
		@RequestBody Grandeur grandeur) {
	try {
		// Set the current date and time before saving
		grandeur.setDateTime(new Date()); // or LocalDateTime.now() if using Java 8 or later

		// Assuming you have a Zone instance with id zoneId
		Zone zone = zoneRepository.findById(zoneId)
				.orElseThrow(() -> new RuntimeException("Zone not found"));

		// Set the Zone on the Grandeur instance
		grandeur.setZone(zone);

		// Assuming GrandeurRepository has a save method
		grandeurRepository.save(grandeur);

		return ResponseEntity.ok("Sensor data received successfully");
	} catch (Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(500).body("Error processing sensor data");
	}
}
*/


	@PostMapping("/update/{id}")
	public RedirectView update(Model model,@PathVariable long id,
			@RequestParam("type") String type, @RequestParam("image") MultipartFile file) {	
		

		int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);
	    String generatedString = randomNum + "";
		
		
		Capteur capteur = capteurService.getCapteur(id);
		if(!type.isEmpty())
			capteur.setType(type);
		if(!file.isEmpty()) {
			StringBuilder fileNames = new StringBuilder();
			String imagePath =  "Capteur_" + generatedString.replace(" ", "_") + "_" + file.getOriginalFilename();
			
			
			Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
			fileNames.append(file.getOriginalFilename()+ "");
			try {
				Files.write(fileNameAndPath, file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}

			capteur.setImage("/uploads/" +  imagePath);
		}
		
		capteurService.saveCapteur(capteur);
		
		return new RedirectView("/farmer/capteurs");
	}
	

	@PostMapping("")
	public RedirectView addCapteur(@RequestParam("branche") String branche, @RequestParam("boitier_id") long boitier_id, @RequestParam("type") String type, @RequestParam("image") MultipartFile file) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);
	    String generatedString = randomNum + "";
		
		if(type.isEmpty())
			return new RedirectView("/farmer/capteurs");
		
		Capteur capteur = new Capteur();
		
		
		StringBuilder fileNames = new StringBuilder();
		String imagePath =  "Capteur_" + generatedString.replace(" ", "_") + "_" + file.getOriginalFilename();
		
		
		Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
		fileNames.append(file.getOriginalFilename()+ "");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		capteur.setImage("/uploads/" +  imagePath);

		capteur.setType(type);
		capteur = capteurService.saveCapteur(capteur);
		/**/
		
		
		
		Connection newConnection = new Connection();
		newConnection.setCapteur(capteur);
		newConnection.setBranche("default");
		newConnection.setFonctionnel(false);
		newConnection = connectionService.saveConnection(newConnection);
		
		Boitier boitier = boiterService.getBoitier(boitier_id);
		boitier.getConnections().add(newConnection);
		boiterService.saveBoitier(boitier);
		
		
		return new RedirectView("/farmer/capteurs");
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable long id) {
		
		try {
			capteurService.supprimer(id);
		} catch (Exception e) {
			
		}
		
		return new RedirectView("/farmer/capteurs");
	}
	
	@GetMapping("/{id}/details")
	public String details(@PathVariable("id") long id, Model model) {
		
		Capteur capteur = capteurService.getCapteur(id);
		model.addAttribute("capteur", capteur);
		return "farmerCapteurDetails.html";
	}
	
	
}
