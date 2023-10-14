package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Plante;
import ma.emsi.smartwatering.repository.PlanteRepository;
@Service
public class PlanteServiceImpl implements PlanteService{
	@Autowired
	private PlanteRepository planteRepo;
	
	
	@Override
	public Plante savePlante(Plante plante) {
		return planteRepo.save(plante);
	}

	@Override
	public List<Plante> getPlante() {
		return planteRepo.findAll();
	}

	@Override
	public Plante get(long id) {
		return planteRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		planteRepo.deleteById(id);
	}
}
