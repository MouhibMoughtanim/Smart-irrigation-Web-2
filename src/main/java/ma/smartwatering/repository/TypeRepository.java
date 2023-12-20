package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.smartwatering.model.SolType;

public interface TypeRepository extends JpaRepository<SolType, Long>{

}
