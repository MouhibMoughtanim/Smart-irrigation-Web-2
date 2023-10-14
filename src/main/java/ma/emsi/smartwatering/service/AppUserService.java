package ma.emsi.smartwatering.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ma.emsi.smartwatering.model.AppUser;

public interface AppUserService extends UserDetailsService {
	
	AppUser saveUser(AppUser user);
	
	AppUser getUser(String username);
	
	List<AppUser> getUsers();

	AppUser getUser(long id);

	void supprimer(long id);
	
	AppUser currentUser();


}
