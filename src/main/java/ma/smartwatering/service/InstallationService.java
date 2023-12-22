package ma.smartwatering.service;

import java.util.List;


import ma.smartwatering.model.Installation;


public interface InstallationService {
	
	Installation saveInstallation(Installation installation);
	
	List<Installation> getInstallations();

	Installation getInstallation(long id);

	void supprimer(long id);
}
