package com.nbc.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AbstractController {
	
	@RequestMapping(value="/")
	public String homePage(){
		return "Welcome to Plan index Programmatic Microservices!!";
	}
	
}
