package ma.smartwatering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ma.smartwatering.model.Capteur;

public interface CapteurRepository extends JpaRepository<Capteur, Long>{
	
	@Query(value = "SELECT Cp.* FROM `zone` Z, `zone_installations` IZ, `installation` I, `boitier` B, `espace_vert` E, `capteur` Cp, `connection` C "
			+ "WHERE E.id = Z.espace_id AND Z.id = IZ.zone_id "
			+ "AND IZ.installations_id = I.id AND I.boitier_id = B.id "
			+ "AND C.boitier_id = B.id AND C.capteur_id = Cp.id AND E.user_id = :user_id group by Cp.id", nativeQuery = true)
	List<Capteur> getCapteurByUserId(long user_id);
}
	