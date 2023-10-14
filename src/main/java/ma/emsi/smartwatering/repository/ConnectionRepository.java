package ma.emsi.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long>{

}
	