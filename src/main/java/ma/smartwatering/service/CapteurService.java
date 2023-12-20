package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.Capteur;


public interface CapteurService {
	
	Capteur saveCapteur(Capteur capteur);
	
	List<Capteur> getCapteurs();

	Capteur getCapteur(long id);

	void supprimer(long id);

	List<Capteur> getCapteurByUserId(long id);
}
