package com.authentication.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.login.entities.User;
import com.authentication.login.services.JwtService;

@RestController
@RequestMapping("demo/")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping("/aboutDet")
	public String aboutDetails() {
		return "This is login Application.";
	}
	
	@GetMapping("/home/{name}")
	public String homeDetails(@PathVariable String name) {
		return "Welcome to Login Application! "+name;
	}
	
	@PostMapping("/generateToken")
	public String authenticateAndGetToken(@RequestBody User user) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
		if(authentication.isAuthenticated()) {
			return jwtService.generateToken(user.getEmail());
		}
		else 
			throw new UsernameNotFoundException("Invalid Credentials!");
	}

}
