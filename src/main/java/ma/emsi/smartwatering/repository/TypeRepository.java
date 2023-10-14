package ma.emsi.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.emsi.smartwatering.model.SolType;

public interface TypeRepository extends JpaRepository<SolType, Long>{

}
