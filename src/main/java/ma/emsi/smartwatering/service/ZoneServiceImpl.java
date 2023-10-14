package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.emsi.smartwatering.model.Zone;
import ma.emsi.smartwatering.repository.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService{

	@Autowired
	private ZoneRepository zoneRepo;
	
	@Override
	public Zone saveZone(Zone zone) {
		return zoneRepo.save(zone);
	}

	@Override
	public List<Zone> getZones() {
		return zoneRepo.findAll();
	}


	@Override
	public Zone get(long id) {
		return zoneRepo.findById(id).get();
	}

	@Override
	public List<Zone> getZoneByUserId(long id) {
		return zoneRepo.getZoneByUserId(id);
	}
}
