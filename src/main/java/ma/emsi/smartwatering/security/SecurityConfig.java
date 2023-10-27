package ma.emsi.smartwatering.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ma.emsi.smartwatering.service.AppUserService;

@Configuration @EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	AppUserService userService;
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder);
		return auth;
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws
			Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 http.formLogin().defaultSuccessUrl("/", true);
			http.httpBasic()
		 .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
		 .and().authorizeRequests()
		 	.mvcMatchers("/inscription","/register", "/plantes", "/uploads", "/grandeurs/**", "/uploads/**","/","/login", "/logout", "/images/**", "/vendor/**",
		 			"/js/**", "/bundles/**", "/charts/**", "/vendor/**", "/css/**").permitAll()
		 	.mvcMatchers(  "/zones/grandeurs/**","/realtime/**", "/farmer", "/farmer/**", "/api/farmer/**").hasRole("USER")
					.mvcMatchers("/users/update/**","/plantes","/plantes/new","/plantes/**").access("hasRole('USER') or hasRole('ADMIN')")

		 	.mvcMatchers("/zones/grandeurs/**", "/**", "/api/**").hasRole("ADMIN")
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