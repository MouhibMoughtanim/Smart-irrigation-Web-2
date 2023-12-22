package ma.emsi.smartwatering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import ma.emsi.smartwatering.model.Grandeur;
import ma.emsi.smartwatering.model.Zone;

public interface GrandeurRepository extends JpaRepository<Grandeur, Long>{

	@Query(value = "SELECT * FROM `grandeur` where zone_id = :zone_id ", nativeQuery = true)
	List<Grandeur> getGrandeurByZone(long zone_id);
}
	