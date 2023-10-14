package ma.emsi.smartwatering.service;


import java.util.List;

import ma.emsi.smartwatering.model.SolType;

public interface TypeService {

	SolType saveType(SolType type);
	
	List<SolType> getTypes();

	SolType getType(long id);

	void supprimer(long id);

}
