package com.authentication.login.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.authentication.login.filter.JwtAuthFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationImpl {
	
	@Autowired
	JwtAuthFilter jwtAuthFilter;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.csrf(csrf->csrf.disable())
			.cors(cors->cors.configurationSource(request->{
				CorsConfiguration config = new CorsConfiguration();
				config.setAllowedOrigins(List.of("http://localhost:8082/greetings"));
				config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
				config.setAllowedHeaders(List.of("Authorization","Content-Type"));
				config.setAllowCredentials(true);
				return config;
			}))
			.authorizeHttpRequests(auth->auth.requestMatchers("/demo/home/*").hasRole("ADMIN")
														.requestMatchers("/demo/about").authenticated()
														.requestMatchers(HttpMethod.OPTIONS,"/**").authenticated()
														.anyRequest().permitAll())
			.sessionManagement(sess->sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider())
			.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCrptBCryptPasswordEncoder());
		//provider.setUserDetailsService(userDetailsService());
		return provider;
	}
	
	@Bean
	public BCryptPasswordEncoder bCrptBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
}
