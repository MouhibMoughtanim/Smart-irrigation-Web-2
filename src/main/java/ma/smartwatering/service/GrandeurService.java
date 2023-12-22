package ma.smartwatering.service;

import java.util.List;


import ma.smartwatering.model.Grandeur;


public interface GrandeurService {
	
	Grandeur saveGrandeur(Grandeur grandeur);
	
	List<Grandeur> getGrandeurs();

	Grandeur getGrandeur(long id);

	void supprimer(long id);
}
