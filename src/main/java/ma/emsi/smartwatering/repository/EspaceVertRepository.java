package ma.emsi.smartwatering.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ma.emsi.smartwatering.model.EspaceVert;

public interface EspaceVertRepository extends JpaRepository<EspaceVert, Long> {

	@Query(value="Select * from espace_vert where user_id is null", nativeQuery=true)
	List<EspaceVert> getNonAssignedSpaces();

}
