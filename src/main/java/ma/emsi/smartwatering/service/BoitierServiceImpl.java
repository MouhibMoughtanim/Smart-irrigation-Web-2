package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Boitier;
import ma.emsi.smartwatering.model.Connection;
import ma.emsi.smartwatering.repository.BoitierRepository;
import ma.emsi.smartwatering.repository.ConnectionRepository;

@Service
public class BoitierServiceImpl implements BoitierService{

	@Autowired
	private BoitierRepository boitierRepo;
	
	@Override
	public Boitier saveBoitier(Boitier boitier) {
		return boitierRepo.save(boitier);
	}

	@Override
	public List<Boitier> getBoitiers() {
		return boitierRepo.findAll();
	}

	@Override
	public Boitier getBoitier(long id) {
		return boitierRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		boitierRepo.deleteById(id);
	}

	@Override
	public List<Boitier> getBoitierByUserId(long user_id) {
		return boitierRepo.getBoitierByUserId(user_id);
	}

}
