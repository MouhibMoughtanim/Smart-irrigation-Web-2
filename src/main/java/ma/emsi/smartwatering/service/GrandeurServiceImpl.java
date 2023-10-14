package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.repository.GrandeurRepository;

@Service
public class GrandeurServiceImpl implements GrandeurService{

	@Autowired
	private GrandeurRepository grandeurRepo;
	
	@Override
	public Grandeur saveGrandeur(Grandeur grandeur) {
		return grandeurRepo.save(grandeur);
	}

	@Override
	public List<Grandeur> getGrandeurs() {
		return grandeurRepo.findAll();
	}

	@Override
	public Grandeur getGrandeur(long id) {
		return grandeurRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		grandeurRepo.deleteById(id);
	}

}
