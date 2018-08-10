package com.nbc.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@ApiIgnore
public class AbstractController {
	
	@RequestMapping(value="/")
	public String homePage(){
		return "Welcome to Convergence Search Programmatic Microservices!!";
	}
	
}
