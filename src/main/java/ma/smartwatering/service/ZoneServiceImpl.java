package ma.smartwatering.service;

import java.util.List;
import java.util.Optional;

import ma.smartwatering.model.Zone;
import ma.smartwatering.repository.ZoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class ZoneServiceImpl implements ZoneService{


	private final ZoneRepository zoneRepo;
	@Autowired
	public ZoneServiceImpl(ZoneRepository zoneRepo) {
		this.zoneRepo = zoneRepo;
	}

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
		if (id <= 0) {
			throw new IllegalArgumentException("Invalid user ID: " + id);
		}

		Optional<List<Zone>> optionalZones = Optional.ofNullable(zoneRepo.getZoneByUserId(id));

		return optionalZones.orElseThrow(() ->  new EntityNotFoundException("No Zones found with the specified ID: " + id));
	}

	@Override
	public void supprimer(long id) {
		zoneRepo.deleteById(id);
	}
}
