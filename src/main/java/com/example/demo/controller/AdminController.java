package com.example.demo.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.Schoolman;
import com.example.demo.service.ComplexSearchService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;

@Controller
public class AdminController {
	@Autowired
	LoginService loginService;
	@Autowired
	RegisterService registerService;
	@Autowired
	ComplexSearchService complexSearchService;
	
	@RequestMapping(value="/admin/Schoolman/ToExamine") // 用于提交审核一个等待审核的招聘者全部信息
	public String adminSchoolmanToExamine(@SessionAttribute(value = "schoolman") Schoolman schoolman,@RequestParam(value = "yesorno") String yesorno){
		schoolman.setYesorno(yesorno);
		registerService.saveSchoolman(schoolman);
		return "redirect:/admin/Schoolmen/Load";
	}
	
	@RequestMapping(value="/admin/Schoolman/Load") // 用于加载一个等待审核的招聘者全部信息
	public String adminSchoolmanLoad(@RequestParam(value = "id") String d, HttpSession session){
		Integer id=Integer.parseInt(d);
		Schoolman schoolman = loginService.findSchoolmanById(id);
		session.setAttribute("schoolman", schoolman);
		return "adminSchoolman.html";
	}
	
	@RequestMapping(value="/admin/Schoolmen/Load") // 用于加载等待审核的招聘者信息
	public String adminSchoolmenLoad(HttpSession session) {
		ArrayList<Schoolman> schoolmenlist=complexSearchService.findSchoolmenByUptimeIsNotNullAndYesornoIsNull();
		session.setAttribute("schoolmenlist", schoolmenlist);
		return "adminSchoolmen.html";
	}
	
	@RequestMapping(value="/admin/Recruiter/ToExamine") // 用于提交审核一个等待审核的招聘者全部信息
	public String adminRecruiterToExamine(@SessionAttribute(value = "recruiter") Recruiter recruiter,@RequestParam(value = "yesorno") String yesorno){
		recruiter.setYesorno(yesorno);
		registerService.saveRecruiter(recruiter);
		return "redirect:/admin/Recruiters/Load";
	}
	
	@RequestMapping(value="/admin/Recruiter/Load") // 用于加载一个等待审核的招聘者全部信息
	public String adminRecruiterLoad(@RequestParam(value = "id") String d, HttpSession session){
		Integer id=Integer.parseInt(d);
		Recruiter recruiter = loginService.findRecruiterById(id);
		session.setAttribute("recruiter", recruiter);
		return "adminRecruiter.html";
	}
	
	@RequestMapping(value="/admin/Recruiters/Load") // 用于加载等待审核的招聘者信息
	public String adminRecruitersLoad(HttpSession session) {
		ArrayList<Recruiter> recruiterslist=complexSearchService.findRecruitersByUptimeIsNotNullAndYesornoIsNull();
		session.setAttribute("recruiterslist", recruiterslist);
		return "adminRecruiters.html";
	}
	
	@RequestMapping("/admin/Login")//管理员登录
	public String adminLogin(String account, String password, Model m, HttpSession session) {
		if (account.equals("")) {
			m.addAttribute("message1", "请输入账号！");
			return "administratorlogin.html";
		}
		if (password.equals("")) {
			m.addAttribute("message2", "请输入密码！");
			return "administratorlogin.html";
		}
		Admin admin = loginService.findAdmin(account, password);
		if (admin == null) {
			m.addAttribute("message2", "密码错误！");
			return "administratorlogin.html";
		}
		session.setAttribute("administrator", admin);
		return "redirect:/admin/Recruiters/Load";
	}

}
