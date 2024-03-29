package ma.emsi.smartwatering.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthorisationFilter extends OncePerRequestFilter{


	private static final String AUTHORIZATION = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		if(request.getServletPath().equals("/api/login")) {
			filterChain.doFilter(request, response);
		} else {
			String authorizationHeader = request.getHeader("Authorization");
			System.out.println(authorizationHeader);
			if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				
				try {
					String token = authorizationHeader.substring("Bearer ".length());
					Algorithm algorithm = Algorithm.HMAC256("emsi_secret".getBytes());
					JWTVerifier verifier = JWT.require(algorithm).build();
					DecodedJWT decodedJWT = verifier.verify(token);
					String username = decodedJWT.getSubject();
					String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
					System.out.println(roles[0]);
					Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
					authorities.add(new SimpleGrantedAuthority(roles[0]));
					
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
					filterChain.doFilter(request, response);
				} catch (Exception e) {
					System.out.println("nn");
					response.setHeader("error", e.getMessage());
					response.setStatus(403);
					Map<String, String> error = new HashMap<>();
					error.put("error_message", e.getMessage());
					
					response.setContentType("application/json");
					new ObjectMapper().writeValue(response.getOutputStream(), error);
				}
				
			} else {
				filterChain.doFilter(request, response);
			}
		}
		
	}

}
