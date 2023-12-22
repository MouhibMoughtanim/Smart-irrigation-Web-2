package ma.emsi.smartwatering.repository;

import ma.emsi.smartwatering.model.mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesureRepository extends JpaRepository<mesure, Long> {
    // Vous pouvez ajouter des méthodes de requête personnalisées ici si nécessaire

}
