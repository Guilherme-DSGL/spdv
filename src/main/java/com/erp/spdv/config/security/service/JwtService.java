package com.erp.spdv.config.security.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.erp.spdv.config.security.core.SecurityConstants;
import com.erp.spdv.model.entity.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	public String extractJwtFromHeader(String authHeader) {
		return authHeader.substring(SecurityConstants.ID_SUBSTRING_AUTH_HEADER);
	}

	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public String extractRole(String token) {
		final Claims claims = extractAllClaims(token);
		return claims.get("role", String.class);
	}
	
	public <T>T extractClaim(String token, Function<Claims, T> claimsresolver){
		final Claims claims = extractAllClaims(token);
		return claimsresolver.apply(claims);
	}
	
	public String generateToken(User user) {
		Map<String, String> extraClaims = new HashMap<>();
		extraClaims.put("role", user.getRole().toString());
		return generateToken(extraClaims, user);
	}
	
	public String generateToken(Map<String, String> extraClaims,
			UserDetails userDetails) {
		return Jwts
				.builder()
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 * 60 * 24))
				.signWith(getSignInKey(), SignatureAlgorithm.HS256)
				.compact();
	}
	
	public boolean isTokenValid(String token, UserDetails userDetails) {
		
		final String userName = extractUserName(token);
		final String role = extractRole(token);
		final boolean rolesIsEquals = userDetails.getAuthorities().toString().contains(role);
 		return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token) && rolesIsEquals;
	}
	
	private boolean isTokenExpired(String token) {	
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private Claims extractAllClaims(String token) {
		return Jwts
				.parserBuilder()
				.setSigningKey(getSignInKey())
				.build()
				.parseClaimsJws(token)
				.getBody();
	}
	
	private Key getSignInKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SecurityConstants.SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}
