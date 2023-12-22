package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.smartwatering.model.Plantage;

public interface PlantageRepository extends JpaRepository<Plantage, Long>{
}
