package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.EspaceVert;
import ma.smartwatering.repository.EspaceVertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EspaceVertServiceImpl implements EspaceVertService{


	private final EspaceVertRepository espaceRepo;
	@Autowired
	public EspaceVertServiceImpl(EspaceVertRepository espaceRepo) {
		this.espaceRepo = espaceRepo;
	}

	@Override
	public EspaceVert saveEspaceVert(EspaceVert espace) {
		return espaceRepo.save(espace);
	}

	@Override
	public List<EspaceVert> getEspacesVerts() {
		return espaceRepo.findAll();
	}

	@Override
	public EspaceVert get(long id) {
		return espaceRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		espaceRepo.deleteById(id);
	}

	@Override
	public List<EspaceVert> getNonAssignedSpaces() {
		return espaceRepo.getNonAssignedSpaces();
	}

}
