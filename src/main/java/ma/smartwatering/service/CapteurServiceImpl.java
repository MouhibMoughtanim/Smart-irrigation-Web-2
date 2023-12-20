package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.repository.CapteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.smartwatering.model.Capteur;

@Service
public class CapteurServiceImpl implements CapteurService{



	private final CapteurRepository capteurRepo;
	@Autowired
	public CapteurServiceImpl(CapteurRepository capteurRepo) {
		this.capteurRepo = capteurRepo;
	}

	@Override
	public Capteur saveCapteur(Capteur capteur) {
		return capteurRepo.save(capteur);
	}

	@Override
	public List<Capteur> getCapteurs() {
		return capteurRepo.findAll();
	}

	@Override
	public Capteur getCapteur(long id) {
		return capteurRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		capteurRepo.deleteById(id);
	}

	@Override
	public List<Capteur> getCapteurByUserId(long id) {
		return capteurRepo.getCapteurByUserId(id);
	}

}
