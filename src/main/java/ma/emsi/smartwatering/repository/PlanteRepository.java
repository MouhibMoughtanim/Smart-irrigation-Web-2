package ma.emsi.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.emsi.smartwatering.model.Plante;

@Repository
public interface PlanteRepository extends JpaRepository<Plante, Long>{

}
