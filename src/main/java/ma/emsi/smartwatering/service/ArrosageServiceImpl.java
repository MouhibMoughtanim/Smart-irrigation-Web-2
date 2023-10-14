package ma.emsi.smartwatering.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Arrosage;
import ma.emsi.smartwatering.repository.ArrosageRepository;

@Service
public class ArrosageServiceImpl implements ArrosageService{

	@Autowired
	ArrosageRepository ArrosageRepo;
	
	@Override
	public Arrosage saveArrosage(Arrosage arrossage) {
		return ArrosageRepo.save(arrossage);
	}



}
