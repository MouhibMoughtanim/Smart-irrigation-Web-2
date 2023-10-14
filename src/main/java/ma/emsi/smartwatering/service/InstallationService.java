package ma.emsi.smartwatering.service;

import java.util.List;


import ma.emsi.smartwatering.model.Installation;


public interface InstallationService {
	
	Installation saveInstallation(Installation installation);
	
	List<Installation> getInstallations();

	Installation getInstallation(long id);

	void supprimer(long id);
}
