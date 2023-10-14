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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
}
