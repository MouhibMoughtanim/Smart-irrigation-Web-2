package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;

@Service
public interface EspaceVertService {
	
	EspaceVert saveEspaceVert(EspaceVert espace);
		
	List<EspaceVert> getEspacesVerts();

	EspaceVert get(long id);

	void supprimer(long id);

	List<EspaceVert> getNonAssignedSpaces();
}
