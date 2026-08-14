package com.example.secureservice.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

public class KeycloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

	@Override
	public Collection<GrantedAuthority> convert(Jwt jwt) {

		Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");

		if (realmAccess == null) {
			return Collections.emptyList();
		}

		Object roles = realmAccess.get("roles");

		if (!(roles instanceof Collection<?>)) {
			return Collections.emptyList();
		}

		return ((Collection<?>) roles).stream().map(Object::toString).map(role -> "ROLE_" + role)
				.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
}
