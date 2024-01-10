package ma.emsi.smartwatering.api;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import ma.emsi.smartwatering.model.AppUser;
import ma.emsi.smartwatering.model.EspaceVert;
import ma.emsi.smartwatering.service.AppUserService;
import ma.emsi.smartwatering.service.EspaceVertService;

@RestController @RequestMapping("/api/users")  @RequiredArgsConstructor
public class AppUserController {

	@Autowired
	private AppUserService userService;
	@Autowired
	private EspaceVertService espaceSer;

	
	@PostMapping()
	public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
		return ResponseEntity.ok().body(userService.saveUser(user));
	}
	

}
