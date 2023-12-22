package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.Installation;
import ma.smartwatering.repository.InstallationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstallationServiceImpl implements InstallationService{


	private final InstallationRepository installationRepo;
	@Autowired
	public InstallationServiceImpl(InstallationRepository installationRepo) {
		this.installationRepo = installationRepo;
	}

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
