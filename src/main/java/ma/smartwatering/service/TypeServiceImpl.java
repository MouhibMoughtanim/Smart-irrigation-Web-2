package ma.smartwatering.service;


import java.util.List;

import ma.smartwatering.model.SolType;
import ma.smartwatering.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeServiceImpl implements TypeService{


	private final TypeRepository typeRepo;
	@Autowired
	public TypeServiceImpl(TypeRepository typeRepo) {
		this.typeRepo = typeRepo;
	}

	@Override
	public SolType saveType(SolType plante) {
		return typeRepo.save(plante);
	}


	@Override
	public void supprimer(long id) {
		typeRepo.deleteById(id);
	}


	@Override
	public List<SolType> getTypes() {
		return typeRepo.findAll();
	}


	@Override
	public SolType getType(long id) {
		return typeRepo.getById(id);
	}







	
}
