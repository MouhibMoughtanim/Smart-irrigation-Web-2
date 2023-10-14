package ma.emsi.smartwatering.Usercontroller;

import java.awt.geom.Arc2D.Float;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.model.Plantage;
import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.service.AppUserService;

@Controller
@RequestMapping("/farmer/statistics")
public class StatisticsController {

	AppUserService userService;
	/*
	@GetMapping("")
	public String getStatistics() {	
		
		AppUser user = userService.currentUser();
		
		List<String> zonesName = new ArrayList<String>();
		List<Integer> nombrePlanteZone = new ArrayList<Integer>();
		List<Float> pourcentagePlante = new ArrayList<Float>();
		List<String> plantesName = new ArrayList<String>();
		List<Integer> plantesNumber = new ArrayList<Integer>();
		
		List<Zone> zones = new ArrayList<Zone>();
		for(EspaceVert e: user.getEspacesVerts())
			zones.addAll(e.getZones());
		
		List<Plantage> plantages = new ArrayList<Plantage>();
		for(Zone z: zones) {
			zonesName.add(z.getLibelle());
			int value = 0;
			for(Plantage p: z.getPlantages()) {
				value += p.getNombre();
			}
			
		}
		
		

		return "Done !";
	}
*/

}
