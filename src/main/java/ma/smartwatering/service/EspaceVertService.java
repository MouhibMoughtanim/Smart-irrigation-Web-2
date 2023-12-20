package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.EspaceVert;
import org.springframework.stereotype.Service;

@Service
public interface EspaceVertService {
	
	EspaceVert saveEspaceVert(EspaceVert espace);
		
	List<EspaceVert> getEspacesVerts();

	EspaceVert get(long id);

	void supprimer(long id);

	List<EspaceVert> getNonAssignedSpaces();
}
