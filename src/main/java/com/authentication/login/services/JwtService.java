package com.authentication.login.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
	
	public static final String SECRET = "THISISSECUREDTOKENKEYUSEITCAREFULLY1645454353";
	
	public String generateToken(String userName) {
		Map<String,Object> claims = new HashMap();
		return createToken(claims,userName);
	}
	
	private String createToken(Map<String,Object> claims,String userName) {
		
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis()+1000*60*30)) // Token valid for 30 minutes
					.signWith(getSignKey(),SignatureAlgorithm.HS256)
					.compact();
	}

	private Key getSignKey() {
		// TODO Auto-generated method stub
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);//Converts a Base64-encoded secret into a binary key.
		return Keys.hmacShaKeyFor(keyBytes); // Generates an HMAC key for cryptographic signing
		 
	}
	
	public String extractUsername(String token) {
		return extractClaims(token,Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaims(token,Claims::getExpiration);
	}

	private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
		// TODO Auto-generated method stub
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
		return Jwts.parserBuilder()
				   .setSigningKey(getSignKey())
				   .build()
				   .parseClaimsJws(token)
				   .getBody();
	}
	
	//Check if token is expired
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	//Validate the token detail against user details and expiration
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}
