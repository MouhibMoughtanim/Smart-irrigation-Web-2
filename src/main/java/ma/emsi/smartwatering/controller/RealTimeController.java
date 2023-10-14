package ma.emsi.smartwatering.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.repository.GrandeurRepository;
import ma.emsi.smartwatering.service.ZoneService;

@Controller
@RequestMapping("")
public class RealTimeController {

	
	static Map<Long, SseEmitter> emitters    = new HashMap<Long, SseEmitter>();
	
	@Autowired
	private ZoneService zoneService;
	@Autowired
	GrandeurRepository GrandeurRepo;
	
	@GetMapping("/realtime/{zone_id}")
	public SseEmitter handle(@PathVariable("zone_id") long zone_id) throws IOException 
	{
		
		SseEmitter newEmitter = new SseEmitter((long) 1000 * 60 * 5);
		emitters.put(zone_id, newEmitter);
	     System.out.println("RealTime mode activated \n");
	     List<Grandeur> grandeurs = GrandeurRepo.getGrandeurByZone(zone_id);
	     Collections.reverse(grandeurs);
	     System.out.println("last HM grandeurs : " + grandeurs);
		 
			for(Grandeur g: grandeurs) {
				newEmitter.send(g);
			}

	     return newEmitter;
	}
	

	
	
	static public void  dispatchEventToCleint(long zone_id, Grandeur grandeur) throws IOException {
		
		SseEmitter emitter = emitters.get(zone_id);

		if(emitter != null) {
			emitter.send(grandeur);
		}
	}
}
