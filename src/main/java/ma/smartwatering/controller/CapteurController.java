package ma.smartwatering.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

import ma.smartwatering.model.Capteur;
import ma.smartwatering.service.CapteurService;

@Controller
@RequestMapping("/capteurs")
public class CapteurController {
	

	private final CapteurService capteurService;
	
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/uploads";
	@Autowired
	public CapteurController(CapteurService capteurService) {
		this.capteurService = capteurService;
	}

	@GetMapping("")
	public String login(Model model) {	
		List<Capteur> capteurs = capteurService.getCapteurs();
		
		model.addAttribute("capteurs", capteurs);
			
		return "capteurs.html";
	}
	
	@GetMapping("/update/{id}")
	public String updateForm(Model model,@PathVariable long id) {	
		List<Capteur> capteurs = capteurService.getCapteurs();
		
		model.addAttribute("capteurs", capteurs);
		Capteur updateCapteur = capteurService.getCapteur(id);
		model.addAttribute("updateCapteur", updateCapteur);
		
		return "capteurs.html";
	}
	
	
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
		
		return new RedirectView("/capteurs");
	}
	

	@PostMapping("")
	public RedirectView addCapteur(@RequestParam("type") String type, @RequestParam("image") MultipartFile file) {
		
		int randomNum = ThreadLocalRandom.current().nextInt(1000, 9999 + 1);
	    String generatedString = randomNum + "";
		
		if(type.isEmpty())
			return new RedirectView("/capteurs");
		
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
		capteurService.saveCapteur(capteur);
		
		
		return new RedirectView("/capteurs");
	}
	
	@PostMapping("/delete/{id}")
	public RedirectView delete(@PathVariable long id) {
		
		try {
			capteurService.supprimer(id);
		} catch (Exception e) {
			
		}
		
		return new RedirectView("/capteurs");
	}
	
	@GetMapping("/{id}/details")
	public String details(@PathVariable("id") long id, Model model) {
		
		Capteur capteur = capteurService.getCapteur(id);
		model.addAttribute("capteur", capteur);
		return "capteurDetails.html";
	}
	
	
}
