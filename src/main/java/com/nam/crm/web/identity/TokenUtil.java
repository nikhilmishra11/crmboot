package com.nam.crm.web.identity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.nam.crm.model.jpa.User;
import com.nam.crm.model.jpa.UserRoles;
import com.nam.crm.model.jpa.UserRolesPk;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenUtil {

	// private static final long VALIDITY_TIME_MS = 10 * 24 * 60 * 60 * 1000;// 10
	// days Validity
	private static final long VALIDITY_TIME_MS = 2 * 60 * 60 * 1000; // 2 hours validity
	private static final String AUTH_HEADER_NAME = "Authorization";

	private String secret = "mrin";

	public Optional<Authentication> verifyToken(HttpServletRequest request) {
		final String token = request.getHeader(AUTH_HEADER_NAME);

		if (token != null && !token.isEmpty()) {
			final TokenUser user = parseUserFromToken(token.replace("Bearer", "").trim());
			if (user != null) {
				return Optional.of(new UserAuthentication(user));
			}
		}
		return Optional.empty();

	}

	// Get User Info from the Token
	public TokenUser parseUserFromToken(String token) {

		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		User user = new User();
		user.setUserId((String) claims.get("userId"));
		String roles = (String) claims.get("role");
		String[] roleArr = roles.split(",");
		UserRolesPk pk = null;
		UserRoles userRoles = null;
		Set<UserRoles> set = new HashSet<>();
		for (String role : roleArr) {
			pk = new UserRolesPk(user.getUserId(), role);
			userRoles = new UserRoles();
			userRoles.setUserRolesPk(pk);
			set.add(userRoles);
		}
		user.setUserRoles(set);
		if (user.getUserId() != null && user.getUserRoles() != null) {
			return new TokenUser(user);
		} else {
			return null;
		}
	}

	public String createTokenForUser(TokenUser tokenUser) {
		return createTokenForUser(tokenUser.getUser());
	}

	public String createTokenForUser(User user) {
		return Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + VALIDITY_TIME_MS))
				.setSubject(user.getFullName()).claim("userId", user.getUserId())
				.claim("role", roles(user.getUserRoles())).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public static List<String> rolesList(Set<UserRoles> userRoles) {
		return userRoles.stream().map(r -> r.getUserRolesPk().getRole()).collect(Collectors.toList());
	}

	public static String[] rolesArray(Set<UserRoles> userRoles) {
		List<String> list = userRoles.stream().map(r -> r.getUserRolesPk().getRole()).collect(Collectors.toList());
		return list.toArray(new String[userRoles.size()]);
	}

	public static String roles(Set<UserRoles> userRoles) {
		return userRoles.stream().map(r -> r.getUserRolesPk().getRole()).collect(Collectors.joining(","));
	}
}
