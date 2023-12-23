package ma.emsi.smartwatering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SmartwateringV21Application {

	public static void main(String[] args) {
		SpringApplication.run(SmartwateringV21Application.class, args);
	}

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
