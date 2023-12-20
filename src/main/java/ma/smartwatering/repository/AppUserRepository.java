package ma.smartwatering.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ma.smartwatering.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	
	public AppUser findByUsername(String Username);
	
}
	