package ma.smartwatering.repository;

import java.util.List;

import ma.smartwatering.model.Boitier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoitierRepository extends JpaRepository<Boitier, Long>{
	
	@Query(value = "SELECT B.* FROM "
			+ "`zone` Z, `zone_installations` IZ, `installation` I, `boitier` B, `espace_vert` E "
			+ "WHERE E.id = Z.espace_id AND Z.id = IZ.zone_id "
			+ "AND IZ.installations_id = I.id AND I.boitier_id = B.id AND E.user_id =:user_id group by B.id", nativeQuery = true)
	List<Boitier> getBoitierByUserId(long user_id);
}
	