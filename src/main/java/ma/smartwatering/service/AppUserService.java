package ma.smartwatering.service;

import java.util.List;

import ma.smartwatering.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {
	
	AppUser saveUser(AppUser user);
	
	AppUser getUser(String username);
	
	List<AppUser> getUsers();

	AppUser getUser(long id);

	void supprimer(long id);
	
	AppUser currentUser();


}
