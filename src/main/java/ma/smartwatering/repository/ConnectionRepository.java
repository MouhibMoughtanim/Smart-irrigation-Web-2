package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.smartwatering.model.Connection;

public interface ConnectionRepository extends JpaRepository<Connection, Long>{

}
	