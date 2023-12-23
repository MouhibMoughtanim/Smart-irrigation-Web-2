package ma.emsi.smartwatering.security;

import ma.emsi.smartwatering.security.NoPopupBasicAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import ma.emsi.smartwatering.filter.CustomAuthentificationFilter;
import ma.emsi.smartwatering.filter.CustomAuthorisationFilter;
@Configuration
@EnableWebSecurity

@Order(2)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MobileSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String GET = "get";
    private static final String POST = "post";

    private final UserDetailsService userDetailsService;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    @Qualifier("secondaryPasswordEncoder")
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);




    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
                .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                .and().authorizeRequests()

                .antMatchers("/api/login**", "/api/users/token/refresh").permitAll()
                .antMatchers(GET, "/api/farmer", "/api/farmer/**").permitAll()
                .antMatchers(GET, "/api/users").permitAll()
                .antMatchers(POST, "/api/users**").permitAll()
                .antMatchers(GET, "/api/espace**").permitAll()
                .antMatchers(POST, "/api/espace**").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .clearAuthentication(true).invalidateHttpSession(true)
                .and()
                .csrf().disable();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
