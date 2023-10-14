package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Capteur;
import ma.emsi.smartwatering.model.TypePlante;


public interface CapteurService {
	
	Capteur saveCapteur(Capteur capteur);
	
	List<Capteur> getCapteurs();

	Capteur getCapteur(long id);

	void supprimer(long id);

	List<Capteur> getCapteurByUserId(long id);
}
