package ma.emsi.smartwatering.Usercontroller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
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
import ma.emsi.smartwatering.model.Boitier;
import ma.emsi.smartwatering.model.Capteur;
import ma.emsi.smartwatering.model.Connection;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.model.Installation;
import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.BoitierService;
import ma.emsi.smartwatering.service.CapteurService;
import ma.emsi.smartwatering.service.ConnectionService;
import ma.emsi.smartwatering.service.InstallationService;
import ma.emsi.smartwatering.service.ZoneService;

@Controller
@RequestMapping("/farmer/boitiers")
public class FarmerBoitierController {
	
	@Autowired
	private BoitierService boitierService;
	@Autowired
	private CapteurService capteurService;
	@Autowired
	private ConnectionService connectionService;
	@Autowired
	private AppUserService userService;
	@Autowired
	private ZoneService zoneService;
	@Autowired
	private InstallationService installationService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	
	
	
	@GetMapping("")
	public String farmerSpace(Model model) {
		AppUser user = userService.currentUser();
		List<Boitier> boitiers = boitierService.getBoitierByUserId(user.getId());
		List<Zone> zones = zoneService.getZoneByUserId(user.getId());
		
		model.addAttribute("boitiers", boitiers);
		model.addAttribute("zones", zones);
		
		return "farmerBoitiers.html";
	}
	
	@GetMapping("/update/{id}")
	public String updateForm(Model model,@PathVariable long id) {	
		List<Boitier> boitiers = boitierService.getBoitierByUserId(userService.currentUser().getId());
		
		AppUser user = userService.currentUser();
		model.addAttribute("boitiers", boitierService.getBoitierByUserId(user.getId()));
		
		
		Boitier updateBoitier = boitierService.getBoitier(id);
		if(boitiers.contains(updateBoitier))
			model.addAttribute("updateBoitier", updateBoitier);
		
		return "farmerBoitiers.html";
	}
	
	
	@PostMapping("/update/{id}")
	public RedirectView update(Model model,@PathVariable long id, @RequestParam("ref") String ref, @RequestParam("code") String code,
			@RequestParam("type") String type, @RequestParam("image") MultipartFile file) {	
		
		Boitier boitier = boitierService.getBoitier(id);
		List<Boitier> boitiers = boitierService.getBoitierByUserId(userService.currentUser().getId());
		if(!boitiers.contains(boitier))
			return new RedirectView("/farmer/boitiers");
		
		
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
		
		return new RedirectView("/farmer/boitiers");
	}
	

	@PostMapping("")
	public RedirectView addBoitier(@RequestParam("zone_id") long zone_id, @RequestParam("ref") String ref, @RequestParam("type") String type, @RequestParam("code") String code, @RequestParam("image") MultipartFile file) {
		
		
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
		newBoitier = boitierService.saveBoitier(newBoitier);
		
		Zone zone = zoneService.get(zone_id);
		Installation newInstallation = new Installation();
		
		newInstallation.setBoitier(newBoitier);
		newInstallation.setDateDebut(new Date());
		
		newInstallation = installationService.saveInstallation(newInstallation);
		
		zone.getInstallations().add(newInstallation);
		zoneService.saveZone(zone);
		
		return new RedirectView("/farmer/boitiers");
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable long id) {
		
		try {
			boitierService.supprimer(id);
		} catch (Exception e) {
			
		}
		
		return new RedirectView("/farmer/boitiers");
	}
	
	@GetMapping("/details/{id}")
	public String details(@PathVariable long id, Model model) {
		
		Boitier boitier = boitierService.getBoitier(id);
		List<Capteur> capteurs = capteurService.getCapteurs();
		
		model.addAttribute("boitier", boitier);
		model.addAttribute("capteurs", capteurs);
		model.addAttribute("showPopup",false);

		return "farmerBoitierDetails.html";
	}
	@GetMapping("/detailss/{id}")
	public String detailss(@PathVariable long id, Model model) {

		Boitier boitier = boitierService.getBoitier(id);
		List<Capteur> capteurs = capteurService.getCapteurs();

		model.addAttribute("boitier", boitier);
		model.addAttribute("capteurs", capteurs);
		model.addAttribute("showPopup",true);

		return "farmerBoitierDetails.html";
	}

	@PostMapping("/{id}/capteur")
	public RedirectView capteur(@PathVariable("id") long id,
								@RequestParam("capteur_id") long capteur_id,
								@RequestParam("branche") String branche,
								@RequestParam("fonctionnel") boolean fonctionnel,Model model) {

		Boitier boitier = boitierService.getBoitier(id);
		Capteur capteur = capteurService.getCapteur(capteur_id);

		if (boitier != null && capteur != null) {
			boolean branchExists = boitier.getConnections()
					.stream()
					.anyMatch(connection -> connection.getBranche().equals(branche));

			if (!branchExists ) {
				Connection connection = new Connection();
				connection.setCapteur(capteur);
				connection.setBranche(branche);
				connection.setFonctionnel(fonctionnel);
				connection = connectionService.saveConnection(connection);

				if (boitier.getConnections() == null) {
					boitier.setConnections(new ArrayList<>());
				}

				boitier.getConnections().add(connection);
				boitierService.saveBoitier(boitier);
				return new RedirectView("/farmer/boitiers/details/" + id);

			} else {
				boolean fExists = boitier.getConnections()
						.stream()
						.anyMatch(Connection::isFonctionnel);
				if(!fExists ){

					Connection connection = new Connection();
					connection.setCapteur(capteur);
					connection.setBranche(branche);
					connection.setFonctionnel(fonctionnel);
					connection = connectionService.saveConnection(connection);

					if (boitier.getConnections() == null) {
						boitier.setConnections(new ArrayList<>());
					}

					boitier.getConnections().add(connection);
					boitierService.saveBoitier(boitier);
					return new RedirectView("/farmer/boitiers/details/" + id);

				}	else{

					System.err.println("Branch already exists in the connections");
					return new RedirectView("/farmer/boitiers/detailss/" + id);

				}
			}
		} else {
			System.err.println("Boitier or Capteur not found");
		}

		return new RedirectView("/farmer/boitiers/details/" + id);

	}

	@GetMapping("/{boitier_id}/on/{id}")
	public RedirectView turnOn(@PathVariable("id") long id, @PathVariable("boitier_id") long boitier_id) {
		
		
		List<Boitier> boitiers = boitierService.getBoitierByUserId(userService.currentUser().getId());
		if(!boitiers.contains(boitierService.getBoitier(boitier_id)))
			return new RedirectView("/farmer/boitiers");
		
		
		Connection connection = connectionService.getConnection(id);
		
		connection.setFonctionnel(true);
		connectionService.saveConnection(connection);
		
		return new RedirectView("/farmer/boitiers/details/" + boitier_id);
		
	}
	
	@GetMapping("/{boitier_id}/off/{id}")
	public RedirectView turnOff(@PathVariable("id") long id, @PathVariable("boitier_id") long boitier_id) {
		
		List<Boitier> boitiers = boitierService.getBoitierByUserId(userService.currentUser().getId());
		if(!boitiers.contains(boitierService.getBoitier(boitier_id)))
			return new RedirectView("/farmer/boitiers");
		
		Connection connection = connectionService.getConnection(id);
		
		connection.setFonctionnel(false);
		connectionService.saveConnection(connection);
		
		return new RedirectView("/farmer/boitiers/details/" + boitier_id);
		
	}
	


}
