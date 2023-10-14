package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Plante;
@Service
public interface PlanteService {
	
	Plante savePlante(Plante plante);
	
	List<Plante> getPlante();

	Plante get(long id);

	void supprimer(long id);
}
