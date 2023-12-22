package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.smartwatering.model.Arrosage;

public interface ArrosageRepository extends JpaRepository<Arrosage, Integer>{
	
	
}
	