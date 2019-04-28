package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Graduate;
import com.example.demo.service.FilesaveService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;

@Controller
public class GraduateController {
	@Autowired
	LoginService loginService;
	@Autowired
	RegisterService registerService;
	@Autowired
	FilesaveService filesaveService;
	final String  USER_TYPR="graduate";
	
	@RequestMapping("/graduate/Login")
	public String graduatelogin(String username, String password, Model m, HttpSession session) {
		session.setAttribute("username", username);
		if (username.equals("")) {
			m.addAttribute("message1", "请输入账号！");
			return "graduateslogin.html";
		}
		if (password.equals("")) {
			m.addAttribute("message2", "请输入密码！");
			return "graduateslogin.html";
		}
		Graduate graduate = loginService.findGraduate(username, password);
		if (graduate == null) {
			m.addAttribute("message2", "密码错误！");
			return "graduateslogin.html";
		}
		session.setAttribute("graduate", graduate);
		return "graduates.html";
	}
	
	@RequestMapping(value = "/graduate/Register", method = { RequestMethod.GET, RequestMethod.POST })
	public String graduateRegister(String username, String password1, String password2, 
	@RequestParam(value = "headpic") MultipartFile headpic, Model m,HttpSession session) throws ServletException, IOException {
		session.setAttribute("username", username);	
		Graduate graduate=new Graduate();
		if (username.equals("")) {
			m.addAttribute("message1", "请输入用户名！");
			return "graduatesRegister.html";
		}
		session.setAttribute("username", username);
		graduate.setUsername(username);
		if (password1.equals("")) {
			m.addAttribute("message2", "请输入密码！");
			return "graduatesRegister.html";
		}
		if (password2.equals("")) {
			m.addAttribute("message3", "请输入确认密码！");
			return "graduatesRegister.html";
		}
		if (!password1.equals(password2)) {
			m.addAttribute("message3", "两次输入密码不一致！");
			return "graduatesRegister.html";
		}
		graduate.setPassword(password1);
		ArrayList<Graduate> graduatelist=registerService.findGraduates(username);
		if(!graduatelist.isEmpty()) {
			m.addAttribute("message1", "用户名已被注册！");
			return "graduatesRegister.html";
		}
		if (!headpic.isEmpty()) {
			String headpicname = headpic.getOriginalFilename();
			String suffix = headpicname.substring(headpicname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "graduatesRegister.html";
			}
			graduate.setHeadpic(filesaveService.savePicture(USER_TYPR,graduate.getUsername(), "headpic",suffix,headpic));
		}
		else {
			m.addAttribute("message1", "请选择头像！");
			return "graduatesRegister.html";
		}
		registerService.saveGraduate(graduate);				
		return "graduatesLogin.html";
	}
}
