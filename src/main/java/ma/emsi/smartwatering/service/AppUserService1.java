package ma.emsi.smartwatering.service;

import java.util.List;

import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.AppUser;

public interface AppUserService1 {

    AppUser saveUser(AppUser user);

    AppUser getUser(String username);

    List<AppUser> getUsers();

    AppUser getUser(long id);

    void supprimer(long id);

    void test();
}
