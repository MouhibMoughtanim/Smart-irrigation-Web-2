package ma.emsi.smartwatering.service;

import java.util.List;

import ma.emsi.smartwatering.model.Plantage;

public interface PlantageService {

	Plantage savePlantage(Plantage plantage);
	
	Plantage get(long id);
	
	List<Plantage> getPlantage();
	
	void supprimer(long id);
}
