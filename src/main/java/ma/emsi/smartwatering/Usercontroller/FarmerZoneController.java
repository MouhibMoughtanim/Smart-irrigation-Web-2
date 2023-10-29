package ma.emsi.smartwatering.Usercontroller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part.Type;
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
import ma.emsi.smartwatering.model.Arrosage;
import ma.emsi.smartwatering.model.Boitier;
import ma.emsi.smartwatering.model.Connection;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.model.Installation;
import ma.emsi.smartwatering.model.Plantage;
import ma.emsi.smartwatering.model.Plante;
import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.model.SolType;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.ArrosageService;
import ma.emsi.smartwatering.service.BoitierService;
import ma.emsi.smartwatering.service.EspaceVertService;
import ma.emsi.smartwatering.service.InstallationService;
import ma.emsi.smartwatering.service.PlantageService;
import ma.emsi.smartwatering.service.PlanteService;
import ma.emsi.smartwatering.service.TypeService;
import ma.emsi.smartwatering.service.ZoneService;


@Controller
@RequestMapping("/farmer/zones")
public class FarmerZoneController {

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
	InstallationService installationService;
	@Autowired
	BoitierService boitierService;
	@Autowired
	AppUserService userService;

	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";

	@GetMapping("/{id}")
	public String list(Model model, @PathVariable("id") long id) { // updated


		List<Zone> zones = null;
		EspaceVert espace = espaceService.get(id);
		AppUser user = userService.currentUser();
		if(user.getEspacesVerts().contains(espace))
			zones = espace.getZones();


		model.addAttribute("zones", zones);
		model.addAttribute("espace_id", id);
		return "farmerZonesList.html";
	}

	@GetMapping("/update/{zoneId}")
	public String updateZone(Model model, @PathVariable("zoneId") long zoneId) {
		// Retrieve the existing zone by its ID
		Zone existingZone = zoneService.get(zoneId);

		// Retrieve the list of SolTypes
		List<SolType> types = typeService.getTypes();

		// Add the existing zone and SolTypes to the model
		model.addAttribute("zone", existingZone);
		model.addAttribute("types", types);

		return "farmerUpdateZone.html";
	}
	@PostMapping("/delete/{id}")
	public RedirectView deleteZone(@PathVariable long id) {
		try {
			// Check if the zone with the specified ID exists
			Zone zoneToDelete = zoneService.get(id);
			if (zoneToDelete == null) {
				// If the zone doesn't exist, redirect to the zone list
				return new RedirectView("/farmer");
			}

			// Perform the delete operation
			zoneService.supprimer(id);

			// After successfully deleting the zone, redirect to the zone list
			return new RedirectView("/farmer");
		} catch (Exception e) {
			// Handle any exceptions or errors that may occur during the delete operation
			// You can also set up an error page or redirect to a relevant error page
			return new RedirectView("/error");
		}
	}

	@PostMapping("/update/{zoneId}")
	public RedirectView processUpdateZone(@PathVariable("zoneId") long zoneId,
										  @RequestParam("libelle") String libelle,
										  @RequestParam("newType") String newType,
										  @RequestParam("type_id") long type_id,
										  @RequestParam("superficie") float superficie,
										  @RequestParam("image") MultipartFile file) {

		// Retrieve the existing zone
		Zone existingZone = zoneService.get(zoneId);

		// Update the zone's properties based on the form data
		existingZone.setLibelle(libelle);
		existingZone.setSuperficie(superficie);

		if (type_id == -1) {
			// Handle creating a new SolType
			SolType t = new SolType();
			t.setName(newType);
			SolType nt = typeService.saveType(t);
			existingZone.setType(nt);
		} else {
			// Handle selecting an existing SolType
			SolType t = typeService.getType(type_id);
			existingZone.setType(t);
		}

		// Handle image upload if needed
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

		// Save the updated zone
		zoneService.saveZone(existingZone);

		// Redirect to the zone's details page
		return new RedirectView("/farmer/zones/" + zoneId+"/details");
	}

	@GetMapping("/new/{id}")
	public String newZone(Model model, @PathVariable("id") long id) {
		List<SolType> types = typeService.getTypes();
		model.addAttribute("types", types);
		model.addAttribute("espace_id", id);
		return "farmerNewZone.html";
	}

	@PostMapping("/new/{id}")
	public RedirectView addZone(@PathVariable("id") long id, @RequestParam("libelle") String libelle, @RequestParam("newType") String newType,
								@RequestParam("type_id") long type_id, @RequestParam("superficie") long superficie, @RequestParam("image") MultipartFile file) {

		EspaceVert espace = espaceService.get(id);
		AppUser user = userService.currentUser();
		if(!user.getEspacesVerts().contains(espace))
			return new RedirectView("/farmer");


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

		espace.getZones().add(newZone);
		espaceService.saveEspaceVert(espace);

		return new RedirectView("/farmer/zones/"+id);
	}

	@GetMapping("/plantage/{id}")
	public String newPlante(Model model, @PathVariable long id) {
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("zone_id", id);
		model.addAttribute("plantes", plantes);
		return "farmerZonePlante.html";
	}
	@GetMapping("/planting/{id}")
	public String newPlantee(Model model, @PathVariable long id) {
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("zone_id", id);
		model.addAttribute("plantes", plantes);
		return "farmerZonePlante.html";
	}
	@PostMapping("/plantage/{id}")
	public RedirectView addPlantage(@PathVariable("id") long id, @RequestParam("plante_id") long plante_id, @RequestParam("quantity") int quantity,
									@RequestParam("date") String date) throws ParseException {

		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();
		if(!zoneService.getZoneByUserId(user.getId()).contains(zone))
			return new RedirectView("/farmer");


		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		if(quantity < 0)
			return new RedirectView("/zones/plantage/"+id);


		Plante plante = planteService.get(plante_id);
		Plantage plantage = new Plantage();
		plantage.setNombre(quantity);
		plantage.setDate(d);
		plantage.setPlante(plante);
		plantage = plantageService.savePlantage(plantage);
		zone.getPlantages().add(plantage);
		zoneService.saveZone(zone);

		return new RedirectView("/farmer/zones/"+id + "/details");
	}
	
	/*@GetMapping("/{id}/grandeurs")
	public String grandeurs() {	
		return "grandeursZone.html";
	}*/

	@GetMapping("/{id}/grandeurs")
	public String grandeurs() {
		return "grandeursZone2.html";
	}

	@GetMapping("/{id}/details")
	public String details(@PathVariable("id") long id, Model model) {

		Zone zone = zoneService.get(id);
		model.addAttribute("zone", zone);
		return "farmerZoneDetails.html";
	}
	@GetMapping("/{zone_id}/modifier-plantage/{id}")
	public String modifierPlantage(Model model, @PathVariable Long id,@PathVariable Long zone_id) {
		Plantage plantages= plantageService.get(id);

		System.out.println(plantages);
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("plantes", plantes);
		model.addAttribute("plantages", plantages);

		model.addAttribute("zone_id", zone_id);

		return "zonePlanteModifUser.html";
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



		return new RedirectView("/farmer/zones/"+zone_id + "/details");
	}

	@PostMapping("/{zone_id}/supprimer/{plantage_id}")
	public RedirectView supprimerPlantage(@PathVariable long zone_id, @PathVariable long plantage_id) {
		Plantage plantage = plantageService.get(plantage_id);
		if (plantage != null) {
			Zone zone = zoneService.get(zone_id);
			zone.getPlantages().remove(plantage);
			plantageService.supprimer(plantage_id);


			return new RedirectView("/farmer/zones/" + zone_id + "/details");
		} else {
			return new RedirectView("/farmer/zones/" + zone_id + "/details?error=Plantage introuvable");
		}
	}
	@PostMapping("/{id}/arrosage")
	public RedirectView arrosage(@PathVariable("id") long id, @RequestParam("litres") float litres, @RequestParam String date) throws ParseException {
		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();
		if(!zoneService.getZoneByUserId(user.getId()).contains(zone))

			return new RedirectView("/farmer");
		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Arrosage arrosage = new Arrosage();
		arrosage.setDate(d);
		arrosage.setLitresEau(litres);
		arrosage = arrosageService.saveArrosage(arrosage);
		zone.getArrosages().add(arrosage);
		zoneService.saveZone(zone);

		return new RedirectView("/farmer/zones/"+id+"/details");
	}

	@GetMapping("/{id}/boitiers")
	public String boitiers(@PathVariable("id") long id, Model model) {

		List<Installation> installations = installationService.getInstallations();
		List<Boitier> boitiers = boitierService.getBoitiers();

		model.addAttribute("installations", installations);
		model.addAttribute("boitiers", boitiers);
		Zone zone = zoneService.get(id);
		model.addAttribute("zone", zone);

		return "farmerZoneBoitiers.html";
	}

	@PostMapping("/install/{id}")
	public RedirectView install(@PathVariable("id") long id,@RequestParam("boitier_id") long boitier_id, @RequestParam String date) throws ParseException {

		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();
		if(!zoneService.getZoneByUserId(user.getId()).contains(zone))
			return new RedirectView("/farmer");


		Installation newInstallation = new Installation();
		Boitier boitier = boitierService.getBoitier(boitier_id);


		for(Installation i : zone.getInstallations()) {
			if(i.getDateFin() == null) {
				i.desinstaller();
				installationService.saveInstallation(i);
			}
		}

		newInstallation.setBoitier(boitier);
		newInstallation.setDateDebut(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		installationService.saveInstallation(newInstallation);

		zone.getInstallations().add(newInstallation);

		zoneService.saveZone(zone);

		return new RedirectView("/farmer/zones/"+id+"/boitiers");
	}

	@PostMapping("/{zone_id}/uninstall/{id}")
	public RedirectView uninstall(@PathVariable("id") long id, @PathVariable("zone_id") long zone_id) {

		Zone zone = zoneService.get(zone_id);
		AppUser user = userService.currentUser();
		if(!zoneService.getZoneByUserId(user.getId()).contains(zone))
			return  new RedirectView("/farmer/zones/"+zone_id+"/boitiers");


		Installation installation = installationService.getInstallation(id);

		installation.desinstaller();
		installationService.saveInstallation(installation);

		return  new RedirectView("/farmer/zones/"+zone_id+"/boitiers");
	}

	@PostMapping("/{zone_id}/delete/{id}")
	public RedirectView deleteInstallation(@PathVariable("id") long id, @PathVariable("zone_id") long zone_id) {

		Zone zone = zoneService.get(zone_id);
		AppUser user = userService.currentUser();
		if(!zoneService.getZoneByUserId(user.getId()).contains(zone))
			return  new RedirectView("/farmer/zones/"+zone_id+"/boitiers");

		Installation installation = installationService.getInstallation(id);

		zone.getInstallations().remove(installation);
		zoneService.saveZone(zone);



		return  new RedirectView("/farmer/zones/"+zone_id+"/boitiers");
	}
	@GetMapping("/{id}/modifier-arrosage/{arrosageId}")
	public String modifierArrosage(@PathVariable("id") long id, @PathVariable("arrosageId") int arrosageId, Model model) {
		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();
		if (!zoneService.getZoneByUserId(user.getId()).contains(zone)) {
			return "redirect:/farmer";
		}
		Arrosage arrosage = arrosageService.getArrosage(arrosageId);
		model.addAttribute("zone", zone);
		model.addAttribute("arrosage", arrosage);
		System.out.println(arrosage);
		System.out.println(arrosage);
		;
		return "farmerModifierArrosage.html";
	}
	@PostMapping("/{id}/modifier-arrosage/{arrosageId}")
	public RedirectView modifierArrosagePost(@PathVariable("id") long id, @PathVariable("arrosageId") int arrosageId,
											 @RequestParam("litres") String litres, @RequestParam String date) throws ParseException {
		Zone zone = zoneService.get(id);
		AppUser user = userService.currentUser();

		if (!zoneService.getZoneByUserId(user.getId()).contains(zone)) {
			return new RedirectView("/farmer");
		}

		Arrosage arrosage = arrosageService.getArrosage(arrosageId);


		Date d = new SimpleDateFormat("yyyy-MM-dd").parse(date);

		arrosage.setDate(d);
		arrosage.setLitresEau(Float.parseFloat(litres));

		arrosageService.saveArrosage(arrosage);

		return new RedirectView("/farmer/zones/" + id + "/details");
	}













}
