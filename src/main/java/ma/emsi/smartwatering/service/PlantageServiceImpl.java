package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Plantage;
import ma.emsi.smartwatering.repository.PlantageRepository;

@Service
public class PlantageServiceImpl implements PlantageService {

	@Autowired
	PlantageRepository plantageRepo;
	
	@Override
	public Plantage savePlantage(Plantage plantage) {
		return plantageRepo.save(plantage);
	}

	@Override
	public Plantage get(long id) {
		return plantageRepo.getById(id);
	}

	@Override
	public List<Plantage> getPlantage() {
		return plantageRepo.findAll();
	}

	@Override
	public void supprimer(long id) {
		plantageRepo.deleteById(id);
	}

}
