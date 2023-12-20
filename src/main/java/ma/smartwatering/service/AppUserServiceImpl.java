package ma.smartwatering.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.smartwatering.model.AppUser;
import ma.smartwatering.repository.AppUserRepository;

@Service  @Transactional @Slf4j
public class AppUserServiceImpl implements AppUserService {
	

	private final AppUserRepository userRepo;
	

	private final PasswordEncoder passwordEncoder;
	@Autowired
	public AppUserServiceImpl(AppUserRepository userRepo, PasswordEncoder passwordEncoder) {
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public AppUser currentUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		
		return getUser(username);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AppUser user = userRepo.findByUsername(username);
		
		
		if (user == null)
			 throw new UsernameNotFoundException("Nom d'utilisateur ou mot de passe erroné");
		
			 
		List<String> roles = new ArrayList<String>();
		roles.add(user.getRole());
	
		 
		return new User(user.getUsername(), user.getPassword(), roles.stream().map(r -> new SimpleGrantedAuthority("ROLE_"+r)).collect(Collectors.toList())); 
		
	}
	
	@Override
	public AppUser saveUser(AppUser user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepo.save(user);
	}

	@Override
	public AppUser getUser(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public List<AppUser> getUsers() {
		return userRepo.findAll();
	}

	@Override
	public AppUser getUser(long id) {
		return userRepo.getById(id);
	}

	@Override
	public void supprimer(long id) {
		userRepo.deleteById(id);
	}

	

}
