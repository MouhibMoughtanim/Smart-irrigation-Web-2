package ma.emsi.smartwatering.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import ma.emsi.smartwatering.service.AppUserService;

@Configuration
@EnableWebSecurity
@Order(1) // Set the order explicitly
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	private static final String GET = "get";
	private static final String POST = "post";

	@Autowired

	AppUserService userService;
	@Autowired
	@Qualifier("primaryPasswordEncoder")
	PasswordEncoder passwordEncoder;





	// Other configurations...


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.formLogin().defaultSuccessUrl("/", true);
		http.httpBasic()
				.authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
				.and().authorizeRequests()
				.mvcMatchers("/inscription","/register", "/plantes", "/uploads", "/grandeurs/**", "/uploads/**","/login", "/logout", "/images/**", "/vendor/**",
						"/js/**", "/bundles/**", "/charts/**", "/vendor/**", "/css/**","/farmer/capteurs/receive").permitAll()
				.mvcMatchers(  "/zones/grandeurs/**","/realtime/**", "/farmer", "/farmer/**","farmer/capteurs/receive/**").hasRole("USER")
				.mvcMatchers("/plantes","/plantes/new","/plantes/**").access("hasRole('USER') or hasRole('ADMIN')")
				.mvcMatchers("/zones/grandeurs/**").hasRole("ADMIN")
				.antMatchers("/api/login**", "/api/users/token/refresh","/api/**").permitAll()
				.antMatchers(GET, "/api/farmer", "/api/farmer/**").permitAll()
				.antMatchers(GET, "/api/users").permitAll()
				.antMatchers(POST, "/api/users**").permitAll()
				.antMatchers(GET, "/api/espace**").permitAll()
				.antMatchers(POST, "/api/espace**","/farmer/capteurs/receive").permitAll()
				.anyRequest().authenticated()
				.and()
				.formLogin().loginPage("/login")
				.and()
				.logout()
				.clearAuthentication(true).invalidateHttpSession(true)
				.and()
				.csrf().disable();
	}







}