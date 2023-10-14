package ma.emsi.smartwatering.api;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ma.emsi.smartwatering.controller.RealTimeController;
import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.repository.GrandeurRepository;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.EspaceVertService;
import ma.emsi.smartwatering.service.GrandeurService;
import ma.emsi.smartwatering.service.ZoneService;

@RestController @RequestMapping("/grandeurs")  @RequiredArgsConstructor
public class GrandeurApiController {


	@Autowired
	GrandeurService grandeurService;
	@Autowired
	ZoneService zoneService;
	
	
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
