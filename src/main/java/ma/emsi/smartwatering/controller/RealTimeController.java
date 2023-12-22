package ma.emsi.smartwatering.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.repository.GrandeurRepository;

@Controller
public class RealTimeController {

	static Map<Long, SseEmitter> emitters = new HashMap<Long, SseEmitter>();

	@Autowired
	private GrandeurRepository grandeurRepository;

	@GetMapping("/realtime/{zone_id}")
	public SseEmitter handle(@PathVariable("zone_id") long zone_id) throws IOException {
		SseEmitter newEmitter = new SseEmitter((long) 1000 * 60 * 5);
		emitters.put(zone_id, newEmitter);

		System.out.println("RealTime mode activated \n");

		List<Grandeur> grandeurs = grandeurRepository.getGrandeurByZone(zone_id);

		System.out.println("last HM grandeurs : " + grandeurs);

		for (Grandeur g : grandeurs) {
			// Convert Grandeur to a JSON format and send it as a String
			String grandeurJson = convertGrandeurToJson(g);
			newEmitter.send(grandeurJson);
		}

		return newEmitter;
	}


	static private String convertGrandeurToJson(Grandeur grandeur) {
		// You need a library like Jackson or Gson to convert Java objects to JSON
		// Here's a simple example using Jackson:
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(grandeur);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "{}"; // Handle the exception appropriately
		}
	}

	static public void dispatchEventToClient(long zone_id, Grandeur grandeur) throws IOException {
		SseEmitter emitter = emitters.get(zone_id);

		if (emitter != null) {
			// Convert Grandeur to a JSON format and send it as a String
			String grandeurJson = convertGrandeurToJson(grandeur);
			emitter.send(grandeurJson);
		}
	}
}
