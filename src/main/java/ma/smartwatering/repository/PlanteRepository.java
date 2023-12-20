package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.smartwatering.model.Plante;

@Repository
public interface PlanteRepository extends JpaRepository<Plante, Long>{

}
