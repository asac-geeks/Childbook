package com.example.finalProject.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class GeneralController {

	@GetMapping("/")
	public String welcome() {
		return "Welcome";
	}
}
