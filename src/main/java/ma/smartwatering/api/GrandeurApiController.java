package ma.smartwatering.api;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ma.smartwatering.model.Grandeur;
import ma.smartwatering.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;
import ma.smartwatering.controller.RealTimeController;
import ma.smartwatering.model.Zone;
import ma.smartwatering.service.GrandeurService;

@RestController @RequestMapping("/grandeurs")
public class GrandeurApiController {



	private final GrandeurService grandeurService;

    private final ZoneService zoneService;
    @Autowired
	public GrandeurApiController(GrandeurService grandeurService, ZoneService zoneService) {
		this.grandeurService = grandeurService;
		this.zoneService = zoneService;
	}

	@GetMapping("/{zone_id}/{type}/{value}")
	public String saveUser(@PathVariable("zone_id") long id, @PathVariable("type") String type,@PathVariable("value") float value) throws IOException{
		
		Grandeur grandeur = new Grandeur();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		
		grandeur.setDate(now);
		grandeur.setValeur(value);
		grandeur.setType(type);
		grandeur = grandeurService.saveGrandeur(grandeur);
		Zone zone = zoneService.get(id);
		zone.getGrandeurs().add(grandeur);
		
		zoneService.saveZone(zone);
		
		RealTimeController.dispatchEventToCleint(id, grandeur);
		
		return grandeur.toString();
	}
	
	@GetMapping("/livedata/{valeur}")
	public String test(@PathVariable("valeur") long valeur) throws IOException{
		
		Grandeur grandeur = new Grandeur();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		
		grandeur.setDate(now);
		grandeur.setValeur(valeur);
		grandeur.setType("temp");
		
		RealTimeController.dispatchEventToCleint(101, grandeur);
		
		return "testing";
	}
	
}
