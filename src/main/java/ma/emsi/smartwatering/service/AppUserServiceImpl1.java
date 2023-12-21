package ma.emsi.smartwatering.service;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.repository.AppUserRepository;

@Service @Transactional @Slf4j
public class AppUserServiceImpl1 implements AppUserService1, UserDetailsService{

    private final AppUserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @ConstructorProperties({"userRepo", "passwordEncoder"})
    public AppUserServiceImpl1(AppUserRepository userRepo, @Qualifier("secondaryPasswordEncoder") BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));

        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public void test() {
        System.out.println("hash : " + passwordEncoder.encode("user"));
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public AppUser getUser(String username) {
        System.out.println("username");
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
