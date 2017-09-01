package com.mycom.SpringAWSLearning.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CopyController {
	
	@RequestMapping("/about")
	public String showAbout()
	{
		return "copy/about";
	}

}
