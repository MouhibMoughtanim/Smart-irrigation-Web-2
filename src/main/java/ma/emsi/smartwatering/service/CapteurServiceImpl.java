package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Capteur;
import ma.emsi.smartwatering.repository.CapteurRepository;

@Service
public class CapteurServiceImpl implements CapteurService{

	@Autowired
	private CapteurRepository capteurRepo;
	
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
