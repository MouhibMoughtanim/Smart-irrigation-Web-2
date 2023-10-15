package ma.emsi.smartwatering.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ma.emsi.smartwatering.model.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import ma.emsi.smartwatering.model.Plante;
import ma.emsi.smartwatering.model.TypePlante;
import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.service.PlanteService;
import ma.emsi.smartwatering.service.TypePlanteService;

@Controller
@RequestMapping("/plantes")
public class PlanteController {

	@Autowired
	private TypePlanteService typeService;
	@Autowired
	private PlanteService planteService;

	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";

	@GetMapping("")
	public String list(Model model) {
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("plantes", plantes);
		return "planteList.html";
	}

	@GetMapping("/new")
	public String newPlante(Model model) {
		List<TypePlante> types = typeService.getTypes();
		model.addAttribute("types", types);
		return "newPlante.html";
	}

	@PostMapping("/new")
	public RedirectView addPlante(@RequestParam("libelle") String libelle, @RequestParam("racine") String racine,
								  @RequestParam("type_id") long type_id, @RequestParam("image") MultipartFile file, @RequestParam("name") String name,
								  @RequestParam("humiditeMax") long humiditeMax, @RequestParam("humiditeMin") long humiditeMin,
								  @RequestParam("temperature") long temperature, @RequestParam("luminosite") long luminosite) {


		if(libelle.isEmpty())
			return new RedirectView("/new");

		if(type_id == -1 && name.isEmpty())
			return new RedirectView("/new");

		StringBuilder fileNames = new StringBuilder();
		String imagePath =  "plante_" + libelle.replace(" ", "_") + "_" + file.getOriginalFilename();
		Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
		fileNames.append(file.getOriginalFilename()+ "");
		try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Plante newPlante = new Plante();
		newPlante.setImage("/uploads/" +  imagePath);
		newPlante.setRacine(racine);
		newPlante.setLibelle(libelle);
		if (type_id != -1) {
			TypePlante type = typeService.getType(type_id);
			newPlante.setType(type);
		} else {
			TypePlante type = new TypePlante();
			type.setHumiditeMax(humiditeMax);
			type.setHumiditeMin(humiditeMin);
			type.setLuminosite(luminosite);
			type.setName(name);
			type.setTemperature(temperature);
			type = typeService.saveType(type);
			newPlante.setType(type);
		}

		planteService.savePlante(newPlante);


		return new RedirectView("/plantes");
	}

	@GetMapping("/update/{id}")
	public String updatePlanteForm(Model model, @PathVariable long id) {
		List<Plante> plantes = planteService.getPlante();


		model.addAttribute("plantes", planteService.getPlante());

		Plante updatePlante = planteService.get(id);
		if (plantes.contains(updatePlante))
			model.addAttribute("updatePlante", updatePlante);

		List<TypePlante> types = typeService.getTypes();
		model.addAttribute("types", types);

		return "planteModifier.html";
	}

	@PostMapping("/update/{id}")
	public RedirectView updatePlante(@PathVariable Long id, @RequestParam("libelle") String libelle,
									 @RequestParam("racine") String racine, @RequestParam("type_id") Long type_id,
									 @RequestParam("image") MultipartFile file, @RequestParam("name") String name,
									 @RequestParam("humiditeMax") String humiditeMax, @RequestParam("humiditeMin") String humiditeMin,
									 @RequestParam("temperature") String temperature, @RequestParam("luminosite") String luminosite) {

		Plante existingPlante = planteService.get(id);
		if (existingPlante == null) {
			return new RedirectView("/plantes/error");
		}

		if (libelle.isEmpty()) {
			return new RedirectView("/plantes/error");
		}

		if (!file.isEmpty()) {
			String imagePath = "plante_" + libelle.replace(" ", "_") + "_" + file.getOriginalFilename();
			Path fileNameAndPath = Paths.get(uploadDirectory, imagePath);
			try {
				Files.write(fileNameAndPath, file.getBytes());
				existingPlante.setImage("/uploads/" + imagePath);
			} catch (IOException e) {
				e.printStackTrace();
				return new RedirectView("/plantes/error");
			}
		}

		existingPlante.setRacine(racine);
		existingPlante.setLibelle(libelle);

		if (type_id != null && type_id != -1) {
			TypePlante type = typeService.getType(type_id);
			existingPlante.setType(type);
		} else if (type_id != null && type_id == -1) {
			TypePlante type = new TypePlante();
			type.setHumiditeMax(Float.parseFloat(humiditeMax));
			type.setHumiditeMin(Float.parseFloat(humiditeMin));
			type.setLuminosite(Float.parseFloat(luminosite));
			type.setName(name);
			type.setTemperature(Float.parseFloat(temperature));
			type = typeService.saveType(type);
			existingPlante.setType(type);
		}

		planteService.savePlante(existingPlante);

		return new RedirectView("/plantes");
	}

	@PostMapping("/delete/{id}")
	public RedirectView deletePlante(@PathVariable long id) {
		try {
			planteService.supprimer(id);
			System.out.println("supprimer");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RedirectView("/plantes");
	}

	@GetMapping("/details/{id}")
	public String planteDetails(@PathVariable long id, Model model) {
		Plante plante = planteService.get(id);
		System.out.println(id+"jjjjjjjjjjj");
		model.addAttribute("updatePlante", plante);
		List<Plante> plantes = planteService.getPlante();
		model.addAttribute("plantes", plantes);
		List<TypePlante> types = typeService.getTypes();
		model.addAttribute("types", types);
		return "PlanteDetails.html";
	}


}













