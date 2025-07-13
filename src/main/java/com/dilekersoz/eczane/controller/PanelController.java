package com.dilekersoz.eczane.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanelController {
	
	@GetMapping("/admin")
	public String adminPanel() {
		return "admin";
	}
	@GetMapping("/user")
	public String userPanel() {
		return "user";
	}
	

}
