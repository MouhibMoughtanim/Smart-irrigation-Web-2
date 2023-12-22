package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.Plantage;
import ma.smartwatering.repository.PlantageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlantageServiceImpl implements PlantageService {


    private final PlantageRepository plantageRepo;
	@Autowired
	public PlantageServiceImpl(PlantageRepository plantageRepo) {
		this.plantageRepo = plantageRepo;
	}

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
