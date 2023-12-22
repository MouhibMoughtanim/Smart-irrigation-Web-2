package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.Plante;
import org.springframework.stereotype.Service;

@Service
public interface PlanteService {
	
	Plante savePlante(Plante plante);
	
	List<Plante> getPlante();

	Plante get(long id);

	void supprimer(long id);
}
