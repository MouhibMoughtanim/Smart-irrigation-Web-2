package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.TypePlante;


public interface TypePlanteService {
	
	TypePlante saveType(TypePlante type);
	
	List<TypePlante> getTypes();

	TypePlante getType(long id);

	void supprimer(long id);
}
