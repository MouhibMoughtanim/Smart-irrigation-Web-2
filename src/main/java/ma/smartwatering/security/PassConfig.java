package ma.smartwatering.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PassConfig {

	@Bean
	@Primary
	public PasswordEncoder primaryPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public BCryptPasswordEncoder secondaryPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
