package com.futsal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RequestMapping("/api/test")
@RestController
public class TestController {
	
	@GetMapping("/all")
	public String allAccess() {
		return "public content";
	}
	 
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER')")
	public String userAccess() {
		return "User content";
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "admin content";
	}

}
