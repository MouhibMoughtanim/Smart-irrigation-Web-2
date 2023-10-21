package ma.emsi.smartwatering.service;

import java.util.List;

import ma.emsi.smartwatering.model.Zone;


public interface ZoneService {

	Zone saveZone(Zone zone);
	
	Zone get(long id);
	
	List<Zone> getZones();

	void supprimer(long id);

	List<Zone> getZoneByUserId(long id);
}
