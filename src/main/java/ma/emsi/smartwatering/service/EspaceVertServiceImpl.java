package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.repository.EspaceVertRepository;


@Service
public class EspaceVertServiceImpl implements EspaceVertService{

	@Autowired
	private EspaceVertRepository espaceRepo;
	
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
