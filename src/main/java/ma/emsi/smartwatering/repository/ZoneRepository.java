package ma.emsi.smartwatering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.emsi.smartwatering.model.Zone;

public interface ZoneRepository extends JpaRepository<Zone,Long>{

	@Query(value = "SELECT Z.* FROM "
			+ "`zone` Z, `espace_vert` E "
			+ "WHERE E.id = Z.espace_id AND E.user_id =:user_id group by Z.id", nativeQuery = true)
	List<Zone> getZoneByUserId(long user_id);

}
