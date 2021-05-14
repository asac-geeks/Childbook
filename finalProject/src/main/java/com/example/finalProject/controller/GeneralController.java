package com.example.finalProject.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins= "*",maxAge = 3600)
public class GeneralController {

	@GetMapping("/")
	@ResponseBody()
	public String welcome() {
		return "Welcome";
	}
}
