package com.authentication.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewBasedController {
	
	@GetMapping("/aboutPage")
	@CrossOrigin(origins = "http://localhost:9000")
	public String aboutDetails() {
		return "about";
	}

}
