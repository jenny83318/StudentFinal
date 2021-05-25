package com.web.store.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller     
public class HomeController {
	@RequestMapping("/welcome")
	public String welcome(Model model) {
		model.addAttribute("title", "歡迎蒞臨君雅網路商城!!!");
		model.addAttribute("subtitle", "網路上獨一無二的購物天堂");
		return "welcome";
	}
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
	@GetMapping
	public String toIndexPage()
	{
		return"shop";
	}
}
