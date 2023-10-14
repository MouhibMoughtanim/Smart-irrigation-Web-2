package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Boitier;
import ma.emsi.smartwatering.model.Connection;
import ma.emsi.smartwatering.model.Installation;
import ma.emsi.smartwatering.repository.BoitierRepository;
import ma.emsi.smartwatering.repository.ConnectionRepository;
import ma.emsi.smartwatering.repository.InstallationRepository;

@Service
public class InstallationServiceImpl implements InstallationService{

	@Autowired
	private InstallationRepository installationRepo;
	
	@Override
	public Installation saveInstallation(Installation installation) {
		return installationRepo.save(installation);
	}

	@Override
	public List<Installation> getInstallations() {
		return installationRepo.findAll();
	}

	@Override
	public Installation getInstallation(long id) {
		return installationRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		installationRepo.deleteById(id);
	}

}
