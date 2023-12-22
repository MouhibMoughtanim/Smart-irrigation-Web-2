package ma.smartwatering.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.smartwatering.model.Grandeur;
import ma.smartwatering.repository.GrandeurRepository;
import ma.smartwatering.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Controller
@RequestMapping("")
public class RealTimeController {

	
	static Map<Long, SseEmitter> emitters    = new HashMap<Long, SseEmitter>();
	

	private final ZoneService zoneService;

	private final GrandeurRepository GrandeurRepo;
	@Autowired
	public RealTimeController(ZoneService zoneService, GrandeurRepository grandeurRepo) {
		this.zoneService = zoneService;
		GrandeurRepo = grandeurRepo;
	}

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
