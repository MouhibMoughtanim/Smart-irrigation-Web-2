package ma.smartwatering.service;

import java.util.List;


import ma.smartwatering.model.Boitier;


public interface BoitierService {
	
	Boitier saveBoitier(Boitier boitier);
	
	List<Boitier> getBoitiers();

	Boitier getBoitier(long id);

	void supprimer(long id);
	
	List<Boitier> getBoitierByUserId(long i);
}
