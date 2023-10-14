package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.TypePlante;
import ma.emsi.smartwatering.repository.TypePlanteRepository;
@Service
public class TypePlanteServiceImpl implements TypePlanteService{

	@Autowired
	private TypePlanteRepository typeRepo;
	
	@Override
	public TypePlante saveType(TypePlante type) {
		return typeRepo.save(type);
	}

	@Override
	public List<TypePlante> getTypes() {
		return typeRepo.findAll();
	}

	@Override
	public TypePlante getType(long id) {
		return typeRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		typeRepo.deleteById(id);
	}

}
