package com.example.secureservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.secureservice.filter.CorrelationIdFilter;
import com.example.secureservice.filter.RequestLoggingFilter;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable())
				.authorizeHttpRequests(auth -> auth
						.requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER", "ADMIN")
						.requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
						.requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("ADMIN")
						.requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
						
						.requestMatchers("/actuator/health/**").permitAll()
						.requestMatchers("/actuator/**").hasRole("ADMIN")
						
						.requestMatchers("/swagger-ui/**").permitAll()
						.requestMatchers("/v3/api-docs/**").permitAll()
						
						.anyRequest().authenticated())
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
				.addFilterBefore(new CorrelationIdFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(new RequestLoggingFilter(), CorrelationIdFilter.class);
		return http.build();
	}

	@Bean
	JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
		return converter;
	}
}
