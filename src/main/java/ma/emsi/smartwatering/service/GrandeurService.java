package ma.emsi.smartwatering.service;

import java.util.List;


import ma.emsi.smartwatering.model.Grandeur;


public interface GrandeurService {
	
	Grandeur saveGrandeur(Grandeur grandeur);
	
	List<Grandeur> getGrandeurs();
	List<Grandeur> getGrandeurByZone(long zone_id);


	void supprimer(long id);
}
