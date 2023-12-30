package ma.emsi.smartwatering.controller;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import ma.emsi.smartwatering.model.*;
import ma.emsi.smartwatering.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part.Type;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping("/zones")
public class ZoneController {
	
	@Autowired
	EspaceVertService espaceService;
	@Autowired
	ZoneService zoneService;
	@Autowired
	TypeService typeService;
	@Autowired
	PlanteService planteService;
	@Autowired
	PlantageService plantageService;
	@Autowired
	ArrosageService arrosageService;
	@Autowired
	AppUserService userService;
	@Autowired
	InstallationService installationService;
	@Autowired
	BoitierService boitierService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	
	@GetMapping("/{id}")
	public String list(Model model, @PathVariable("id") long id) {
		
		List<Zone> zones = espaceService.get(id).getZones();
		model.addAttribute("zones", zones);
		model.addAttribute("espace_id", id);
		return "zonesList.html";
	}
	
	@GetMapping("/new/{id}")
	public String newZone(Model model, @PathVariable("id") long id) {	
		List<SolType> types = typeService.getTypes();
		model.addAttribute("types", types);
		model.addAttribute("espace_id", id);
		return "newZone.html";
	}
	
	@PostMapping("/new/{id}")
	public RedirectView addZone(@PathVariable("id") long id, @RequestParam("libelle") String libelle, @RequestParam("newType") String newType,
			@RequestParam("type_id") long type_id, @RequestParam("superficie") long superficie, @RequestParam("image") MultipartFile file) {	

		
		
		StringBuilder fileNames = new StringBuilder();
		String imagePath =  "zone_" + libelle.replace(" ", "_") + "_" + file.getOriginalFilename();
		if(libelle.isEmpty())
			return new RedirectView("/zone/new");
		
		if(type_id == -1 && newType.isEmpty())
			return new RedirectView("/zone/new");
		
		Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
		fileNames.append(file.getOriginalFilename()+ "");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Zone newZone = new Zone();
		newZone.setImage("/uploads/" +  imagePath);
		newZone.setLibelle(libelle);
		newZone.setSuperficie(superficie);
		
		if(type_id == -1) {
			SolType t = new SolType();
			t.setName(newType);
			SolType nt = typeService.saveType(t);
			newZone.setType(nt);
		} else {
			SolType t = typeService.getType(type_id);
			newZone.setType(t);
		}
		
		System.out.print("New Zone = " + newZone);
		
		zoneService.saveZone(newZone);
		
		EspaceVert espace = espaceService.get(id);
		espace.getZones().add(newZone);
		espaceService.saveEspaceVert(espace);
		
		return new RedirectView("/zones/"+id);
	}
	
	@GetMapping("/plantage/{id}")
	public String newPlante(Model model, @PathVariable long id) {
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("zone_id", id);
		model.addAttribute("plantes", plantes);
		return "zonePlante.html";
	}
	@GetMapping("/{zone_id}/modifier-plantage/{id}")
	public String modifierPlantage(Model model, @PathVariable Long id,@PathVariable Long zone_id) {
		Plantage plantages= plantageService.get(id);

		System.out.println(plantages);
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("plantes", plantes);
		model.addAttribute("plantages", plantages);

		model.addAttribute("zone_id", zone_id);

		return "zonePlanteModif.html";
	}
	@PostMapping("/{zone_id}/supprimer/{plantage_id}")
	public RedirectView supprimerPlantage(@PathVariable long zone_id, @PathVariable long plantage_id) {
		// Utilisez le service pour obtenir le plantage par son ID
		Plantage plantage = plantageService.get(plantage_id);
		if (plantage != null) {
			Zone zone = zoneService.get(zone_id);
			zone.getPlantages().remove(plantage);
			plantageService.supprimer(plantage_id);


			return new RedirectView("/zones/" + zone_id + "/details");
		} else {
			return new RedirectView("/zones/" + zone_id + "/details?error=Plantage introuvable");
		}
	}
	@PostMapping("/plantage/{id}")
	public RedirectView addPlantage(@PathVariable("id") long id, @RequestParam("plante_id") long plante_id, @RequestParam("quantity") int quantity,
			@RequestParam("date") String date) throws ParseException {

	    Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		if(quantity < 0)
			return new RedirectView("/zones/plantage/"+id);


		Plante plante = planteService.get(plante_id);
		Zone zone = zoneService.get(id);
		Plantage plantage = new Plantage();
		plantage.setNombre(quantity);
		plantage.setDate(d);
		plantage.setPlante(plante);
		plantage = plantageService.savePlantage(plantage);
		zone.getPlantages().add(plantage);
		zoneService.saveZone(zone);

		return new RedirectView("/zones/"+id + "/details");
	}
	@PostMapping("/{zone_id}/modifier-plantagee/{id}")
	public RedirectView modifierPlantage(@PathVariable("id") long id, @RequestParam("plante_id") long planteId,@PathVariable("zone_id") long zone_id, @RequestParam("quantity") int quantity,
										 @RequestParam("date") String date) throws ParseException {

		if (quantity < 0) {
			return new RedirectView("/zones/modifierplantage/" + id);
		}
		Plante plante = planteService.get(planteId);

		Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		Plantage existingPlante = plantageService.get(id);
		existingPlante.setPlante(plante);

		System.out.println(existingPlante);

		existingPlante.setNombre(quantity);
		existingPlante.setDate(parsedDate);
		existingPlante = plantageService.savePlantage(existingPlante);



		return new RedirectView("/zones/"+zone_id + "/details");
	}

	/*@GetMapping("/update/{id}")
	public String updateZone(@PathVariable long id, Model model) {
		Zone zone = zoneService.get(id);
		model.addAttribute("zone", zone);
		return "updateZone.html";
	}*/

	@GetMapping("/update/{zoneId}")
	public String updateZone(Model model, @PathVariable("zoneId") long zoneId) {
		Zone existingZone = zoneService.get(zoneId);

		List<SolType> types = typeService.getTypes();

		model.addAttribute("zone", existingZone);
		model.addAttribute("types", types);

		return "updateZone.html";
	}

	@PostMapping("/update/{zoneId}")
	public RedirectView processUpdateZone(@PathVariable("zoneId") long zoneId,
										  @RequestParam("libelle") String libelle,
										  @RequestParam("newType") String newType,
										  @RequestParam("type_id") long type_id,
										  @RequestParam("superficie") float superficie,
										  @RequestParam("image") MultipartFile file) {

		Zone existingZone = zoneService.get(zoneId);

		existingZone.setLibelle(libelle);
		existingZone.setSuperficie(superficie);

		if (type_id == -1) {
			SolType t = new SolType();
			t.setName(newType);
			SolType nt = typeService.saveType(t);
			existingZone.setType(nt);
		} else {
			SolType t = typeService.getType(type_id);
			existingZone.setType(t);
		}

		if (!file.isEmpty()) {
			String imagePath = "zone_" + libelle.replace(" ", "_") + "_" + file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
			try {
				Files.write(fileNameAndPath, file.getBytes());
				existingZone.setImage("/uploads/" + imagePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		zoneService.saveZone(existingZone);

		return new RedirectView("/zones/" + zoneId+"/details");
	}


	@GetMapping("/grandeurs/{id}")
	public String grandeurs() {
		return "grandeursZone2.html";
	}
	
	@GetMapping("/{id}/details")
	public String details(@PathVariable("id") long id, Model model) {
		
		Zone zone = zoneService.get(id);
		model.addAttribute("zone", zone);
		return "zoneDetails.html";
	}
	
	@PostMapping("/{id}/arrosage")
	public RedirectView arrosage(@PathVariable("id") long id, @RequestParam("litres") float litres, @RequestParam String date) throws ParseException {
		
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		
		Arrosage arrosage = new Arrosage();
		arrosage.setDate(d);
		arrosage.setLitresEau(litres);
		
		arrosage = arrosageService.saveArrosage(arrosage);
		
		Zone zone = zoneService.get(id);
		zone.getArrosages().add(arrosage);
		zoneService.saveZone(zone);
		
		return new RedirectView("/zones/"+id+"/details");
	}
	@PostMapping("/{id}/modifier-arrosage/{arrosageId}")
	public RedirectView modifierArrosagePost(@PathVariable("id") long id, @PathVariable("arrosageId") int arrosageId,
											 @RequestParam("litres") String litres, @RequestParam String date) throws ParseException {
		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();


		Arrosage arrosage = arrosageService.getArrosage(arrosageId);


		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		arrosage.setDate(d);
		arrosage.setLitresEau(Float.parseFloat(litres));

		arrosageService.saveArrosage(arrosage);

		return new RedirectView("/zones/" + id + "/details");
	}
	@GetMapping("/{id}/modifier-arrosage/{arrosageId}")
	public String modifierArrosage(@PathVariable("id") long id, @PathVariable("arrosageId") int arrosageId, Model model) {
		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();

		Arrosage arrosage = arrosageService.getArrosage(arrosageId);
		model.addAttribute("zone", zone);
		model.addAttribute("arrosage", arrosage);
		System.out.println(arrosage);
		System.out.println(arrosage);
		;
		return "UserModifierArrosage.html";
	}
	@GetMapping("/{id}/boitier")
	public String boitiers(@PathVariable("id") long id, Model model) {
		
		List<Installation> installations = installationService.getInstallations();
		List<Boitier> boitiers = boitierService.getBoitiers();
		
		model.addAttribute("installations", installations);
		model.addAttribute("boitiers", boitiers);
		Zone zone = zoneService.get(id);
		model.addAttribute("zone", zone);
		return "ZoneBoitiers.html";
	}
	@PostMapping("/{zone_id}//delete/{id}")
	public RedirectView deletePlantage(@PathVariable long id,@PathVariable long zone_id) {
		try {
			plantageService.supprimer(id);
			System.out.println("supprimer");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RedirectView("/plantes");
	}
	@PostMapping("/install/{id}")
	public RedirectView install(@PathVariable("id") long id,@RequestParam("boitier_id") long boitier_id, @RequestParam String date) throws ParseException {
		
		Installation newInstallation = new Installation();
		Boitier boitier = boitierService.getBoitier(boitier_id);
		
		Zone zone = zoneService.get(id);
		
		for(Installation i : zone.getInstallations()) {
			if(i.getDateFin() == null) {
				i.desinstaller();
				installationService.saveInstallation(i);
			}
		}
		newInstallation.setBoitier(boitier);
		newInstallation.setDateDebut(new SimpleDateFormat("yyyy-MM-dd").parse(date));
	  //  newInstallation.in();
		installationService.saveInstallation(newInstallation);
		zone.getInstallations().add(newInstallation);
		//zone.getUActualBoitier();
		Installation uactualBoitier = zone.getUActualBoitier();
		System.out.println("uactualBoitier: " + uactualBoitier);
		zoneService.saveZone(zone);
		return new RedirectView("/zones/"+id+"/boitier");
	}

	@PostMapping("/{zone_id}/uninstall/{id}")
	public RedirectView uninstall(@PathVariable("id") long id, @PathVariable("zone_id") long zone_id) {
		
		Installation installation = installationService.getInstallation(id);
		
		installation.desinstaller();
		installationService.saveInstallation(installation);
		
		return  new RedirectView("/zones/"+zone_id+"/boitier");
	}
	@PostMapping("/delete/{id}")
	public RedirectView deleteZone(@PathVariable long id) {
		try {
			Zone zoneToDelete = zoneService.get(id);
			if (zoneToDelete == null) {
				return new RedirectView("/espaces");
			}

			zoneService.supprimer(id);

			return new RedirectView("/espaces");
		} catch (Exception e) {

			return new RedirectView("/error");
		}
	}



	
	/*@PostMapping("/{zone_id}/delete/{id}")
	public RedirectView deleteInstallation(@PathVariable("id") long id, @PathVariable("zone_id") long zone_id) {
		
		Installation installation = installationService.getInstallation(id);
		
		Zone zone = zoneService.get(zone_id);
		zone.getInstallations().remove(installation);
		zoneService.saveZone(zone);
		
		
		//installationService.supprimer(id);
		
		
		
		return  new RedirectView("/zones/"+zone_id+"/boitiers");
	}*/
	
}
