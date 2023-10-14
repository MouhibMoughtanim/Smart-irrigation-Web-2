package ma.emsi.smartwatering.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.SolType;
import ma.emsi.smartwatering.repository.TypeRepository;

@Service
public class TypeServiceImpl implements TypeService{

	@Autowired
	private TypeRepository typeRepo;
	
	
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
