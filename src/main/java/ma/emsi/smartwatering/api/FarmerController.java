package ma.emsi.smartwatering.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ma.emsi.smartwatering.service.AppUserService1;
import ma.emsi.smartwatering.model.*;
import ma.emsi.smartwatering.repository.AppUserRepository;
import ma.emsi.smartwatering.service.EspaceVertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController @RequestMapping("/api/farmer")  @RequiredArgsConstructor
public class FarmerController {

	@Autowired(required = true)
	private AppUserService1 userSer;
	@Autowired
	EspaceVertService espaceService;
	@Autowired
	private AppUserRepository userRepo;
	@GetMapping()
	public AppUser getFarmer(){
		System.out.println("getFarmer is called!");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(principal);
		String username;
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		return userSer.getUser(username);
		
	}
	
	@GetMapping("/espaces_verts/{username}")
	public List<EspaceVert> getEspace(@PathVariable("username") String username){
		return
				this.userSer.getUser(username).getEspacesVerts();
	}



	@GetMapping("/espaces_verts/{id}/zones")
	public List<Zone> getZones(@PathVariable("id") long id, @PathVariable("username") String username){

		for(EspaceVert e: this.getEspace(username)) {
			if(e.getId() == id) {
				return e.getZones();
			}
		}
		
		return null;
	}
	
	@GetMapping("/zone/{id}")
	public Zone getZone(@PathVariable("id") long id,@PathVariable("username") String username){
		
		for(EspaceVert e: this.getEspace(username)) {
			for(Zone z: e.getZones()) {
				if(z.getId() == id) {
					return z;
				}
			}
		}
		
		return null;
	}
	
	@GetMapping("/zone/{id}/boitiers")
	public List<Installation> getZoneBoitier(@PathVariable("id") long id, @PathVariable("username") String username){
		
		for(EspaceVert e: this.getEspace(username)) {
			for(Zone z: e.getZones()) {
				if(z.getId() == id) {
					return z.getInstallations();
				}
			}
		}
		
		return null;
	}
	
	@GetMapping("/zone/{id}/arrosage")
	public List<Arrosage> getZoneArrosage(@PathVariable("id") long id, @PathVariable("username") String username){
		
		for(EspaceVert e: this.getEspace(username)) {
			for(Zone z: e.getZones()) {
				if(z.getId() == id) {
					return z.getArrosages();
				}
			}
		}
		
		return null;
	}
	
	@GetMapping("/zone/{id}/plante")
	public List<Plantage> getZonePlantes(@PathVariable("id") long id,@PathVariable("username") String username){
		
		for(EspaceVert e: this.getEspace(username)) {
			for(Zone z: e.getZones()) {
				if(z.getId() == id) {
					return z.getPlantages();
				}
			}
		}
		
		return null;
	}
	
	@GetMapping("/zone/{id}/grandeurs")
	public List<Grandeur> getZoneGrandeurs(@PathVariable("id") long id, @PathVariable("username") String username){
		
		for(EspaceVert e: this.getEspace(username)) {
			for(Zone z: e.getZones()) {
				if(z.getId() == id) {
					return z.getGrandeurs();
				}
			}
		}
		
		return null;
	}

	@GetMapping("/user")
	public ResponseEntity<?> getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			Object principal = authentication.getPrincipal();

			if (principal instanceof UserDetails) {
				UserDetails userDetails = (UserDetails) principal;
				String username = userDetails.getUsername();

				// Obtenez les détails de l'utilisateur à partir de votre service
				AppUser user = userSer.getUser(username);

				return ResponseEntity.ok(user);
			}
		}

		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Aucun utilisateur authentifié");
	}
}


