package ma.smartwatering.service;

import ma.smartwatering.repository.ArrosageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.smartwatering.model.Arrosage;

@Service
public class ArrosageServiceImpl implements ArrosageService{


    private final ArrosageRepository ArrosageRepo;
	@Autowired
	public ArrosageServiceImpl(ArrosageRepository arrosageRepo) {
		ArrosageRepo = arrosageRepo;
	}

	@Override
	public Arrosage saveArrosage(Arrosage arrossage) {
		return ArrosageRepo.save(arrossage);
	}

	@Override
	public Arrosage getArrosage(int id) {
		return  ArrosageRepo.getById(id);
	}


}
