package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.Plante;
import ma.smartwatering.repository.PlanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanteServiceImpl implements PlanteService{

	private final PlanteRepository planteRepo;
	@Autowired
	public PlanteServiceImpl(PlanteRepository planteRepo) {
		this.planteRepo = planteRepo;
	}

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
