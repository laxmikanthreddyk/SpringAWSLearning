package com.mycom.SpringAWSLearning.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloWorldController {
	
	@RequestMapping("/")
	public String showHelloWorld()
	{
		return "index";
		
	}

}
