package com.authentication.login.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authentication.login.services.JwtService;
import com.authentication.login.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter{
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	@Lazy
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String authHeader = request.getHeader("Authorization");
		String token = null;
		String username = null;
		
		if(authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7); // Extract token
			username = jwtService.extractUsername(token); // Extract username from token
		}else {
	        System.out.println("Authorization header missing or invalid.");
	    }

		
		// If the token is valid and no authentication is set in the context
		if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userService.loadUserByUsername(username);
			
			// validate token and set authentication
			if(jwtService.validateToken(token, userDetails)) {
				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authToken);
	            System.out.println("User authenticated successfully.");

			}else {
	            System.out.println("Token validation failed.");
	        }
		}
		
		//continue the filter chain
		filterChain.doFilter(request, response);
		
	}

}
