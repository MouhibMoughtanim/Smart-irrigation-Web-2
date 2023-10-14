package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.TypePlante;


public interface TypePlanteService {
	
	TypePlante saveType(TypePlante type);
	
	List<TypePlante> getTypes();

	TypePlante getType(long id);

	void supprimer(long id);
}
