package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class controller {
	
	@RequestMapping("/adminLogin")
	public String administratorLogin() {
		return "adminLogin.html";
	}
	
	@RequestMapping("/schoolmenRegister")
	public String schoolmenRegister() {
		return "schoolmenRegister.html";
	}	
	
	@RequestMapping("/schoolmenLogin")
	public String schoolmenLogin() {
		return "schoolmenLogin.html";
	}
	
	@RequestMapping("/recruitersLogin")
	public String recruitersLogin() {
		return "recruitersLogin.html";
	}
	
	@RequestMapping("/recruitersRegister")
	public String recruitersRegister() {
		return "recruitersRegister.html";
	}

	@RequestMapping("/graduatesLogin")
	public String graduatesLogin() {
		return "graduatesLogin.html";
	}
	
	@RequestMapping("/graduatesRegister")
	public String graduatesRegister() {
		return "graduatesRegister.html";
	}
	@RequestMapping("/test")
	public String test() {
		return "test.html";
	}
}
