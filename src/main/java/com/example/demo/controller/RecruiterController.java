package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.Schoolman;
import com.example.demo.service.ComplexSearchService;
import com.example.demo.service.FilesaveService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;

@Controller
public class RecruiterController {
	@Autowired
	LoginService loginService;
	@Autowired
	RegisterService registerService;
	@Autowired
	FilesaveService filesaveService;
	@Autowired
	ComplexSearchService complexSearchService;
	final String  USER_TYPR="recruiters";
	
	@RequestMapping(value="/recruiter/IdentityStep3",method = { RequestMethod.GET, RequestMethod.POST }) // 用于保存新建的公司的验证照片
	public String recruiterIdentityStep3(@SessionAttribute(value = "recruiter") Recruiter recruiter,@RequestParam(value = "companypicture") MultipartFile companypicture,
			@RequestParam(value = "identitypicture") MultipartFile identitypicture, Model m, HttpSession session) throws ServletException, IOException {
		
		if (!companypicture.isEmpty()) {
			String companypicturename = companypicture.getOriginalFilename();
			String suffix = companypicturename.substring(companypicturename.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "recruiterIdentityStep3.html";
			}
			recruiter.setCompanypicture(filesaveService.savePicture(USER_TYPR,recruiter.getUsername(), "companypicture",suffix,companypicture));
		}else {
			m.addAttribute("message1", "请选择公司营业执照！");
			return "recruiterIdentityStep3.html";
		}
		
		if (!identitypicture.isEmpty()) {
			String identitypicturename = identitypicture.getOriginalFilename();
			String suffix = identitypicturename.substring(identitypicturename.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message2", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "recruiterIdentityStep3.html";
			}
			recruiter.setIdentitypicture(filesaveService.savePicture(USER_TYPR,recruiter.getUsername(), "identitypicture",suffix,identitypicture));
		}
		Date now=new Date();
		recruiter.setUptime(now);//设置上传时间
		session.setAttribute("recruiter", recruiter);
		registerService.saveRecruiter(recruiter);
		return "recruiterPost.html";
	}
	@RequestMapping(value="/recruiter/IdentityStep2",method = { RequestMethod.GET, RequestMethod.POST }) // 用于保存新建的公司
	public String recruiterIdentityStep2(@SessionAttribute(value = "recruiter") Recruiter recruiter,@RequestParam(value = "companylogo") MultipartFile companylogo,
			String companyshortname, String companyfield1, String companyfield2, String companyscale,
			String companydevelop, Model m, HttpSession session) throws ServletException, IOException {
		if (companyshortname.equals("")) {
			m.addAttribute("message2", "请输入公司简称！");
			return "recruiterIdentityStep2.html";
		}
		recruiter.setCompanyshortname(companyshortname);
		if (companyfield1.equals("")) {
			m.addAttribute("message3", "请选择公司业务领域！");
			return "recruiterIdentityStep2.html";
		}
		if (companyfield1.equals(companyfield2)) {
			m.addAttribute("message4", "公司两个业务领域不能相同！");
			return "recruiterIdentityStep2.html";
		}
		String companyfield = companyfield1 + "," + companyfield2;
		recruiter.setCompanyfield(companyfield);
		if (companyscale.equals("")) {
			m.addAttribute("message5", "请选择公司规模大小！");
			return "recruiterIdentityStep2.html";
		}
		recruiter.setCompanyscale(companyscale);
		if (companydevelop.equals("")) {
			m.addAttribute("message6", "请选择公司发展阶段！");
			return "recruiterIdentityStep2.html";
		}
		recruiter.setCompanydevelop(companydevelop);
		if (!companylogo.isEmpty()) {
			String companylogoname = companylogo.getOriginalFilename();
			String suffix = companylogoname.substring(companylogoname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "recruiterIdentityStep2.html";
			}
			recruiter.setCompanylogo(filesaveService.savePicture(USER_TYPR,recruiter.getUsername(), "companylogo",suffix,companylogo));
		}
		else {
			m.addAttribute("message1", "请选择公司logo！");
			return "recruiterIdentityStep2.html";
		}	
		recruiter.setUptime(null);//设置上传时间
		recruiter.setYesorno(null);//重新进行身份确认就将审核状态设置为null,等待管理员审核
		session.setAttribute("recruiter", recruiter);
		registerService.saveRecruiter(recruiter);
		return "recruiterIdentityStep3.html";
	}

	@RequestMapping(value="/recruiter/IdentityStep1",method = { RequestMethod.GET, RequestMethod.POST }) // 用于保存修改后的个人信息
	public String recruiterIdentityStep1(@SessionAttribute(value = "recruiter") Recruiter recruiter, String name,
			@RequestParam(value = "headpic") MultipartFile headpic,String position, String mailbox, String companyname, Model m, HttpSession session)
			throws ServletException, IOException {
		if (name.equals("")) {
			m.addAttribute("message2", "请输入姓名！");
			return "recruiterIdentity.html";
		}
		recruiter.setName(name);
		if (position.equals("")) {
			m.addAttribute("message4", "请输入职位！");
			return "recruiterIdentity.html";
		}
		recruiter.setPosition(position);
		if (mailbox.equals("")) {
			m.addAttribute("message5", "请输入邮箱！");
			return "recruiterIdentity.html";
		}
		recruiter.setMailbox(mailbox);
		if (companyname.equals("")) {
			m.addAttribute("message6", "请输入公司全称！");
			return "recruiterIdentity.html";
		}
		recruiter.setCompanyname(companyname);
		if (!headpic.isEmpty()) {
			String headpicname = headpic.getOriginalFilename();
			String suffix = headpicname.substring(headpicname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "recruiterIdentity.html";
			}
			recruiter.setHeadpic(filesaveService.savePicture(USER_TYPR,recruiter.getUsername(), "headpic",suffix,headpic));
		}
		registerService.saveRecruiter(recruiter);
		session.setAttribute("recruiter", recruiter);
		return "recruiterIdentityStep2.html";
	}

	@RequestMapping(value="/recruiter/updatePersonalInformation" ,method = { RequestMethod.GET, RequestMethod.POST })// 用于保存修改后的个人信息
	public String recruiterupdatePersonalInformation(@SessionAttribute(value = "recruiter") Recruiter recruiter,
			@RequestParam(value = "headpic") MultipartFile headpic, String name, String called, String position,
			String department, String phone, String mailbox, Model m, HttpSession session, HttpServletRequest request)
			throws ServletException, IOException {

		if (name.equals("")) {
			m.addAttribute("message2", "请输入姓名！");
			return "recruiterPersonalInformation.html";
		}
		recruiter.setName(name);
		if (called.equals("")) {
			m.addAttribute("message3", "请输入称呼！");
			return "recruiterPersonalInformation.html";
		}
		recruiter.setCalled(called);
		if (position.equals("")) {
			m.addAttribute("message4", "请输入职位！");
			return "recruiterPersonalInformation.html";
		}
		recruiter.setPosition(position);
		if (department.equals("")) {
			m.addAttribute("message5", "请输入部门！");
			return "recruiterPersonalInformation.html";
		}
		recruiter.setDepartment(department);
		if (phone.equals("")) {
			m.addAttribute("message6", "请输入手机号！");
			return "recruiterPersonalInformation.html";
		}
		recruiter.setPhone(phone);
		if (mailbox.equals("")) {
			m.addAttribute("message7", "请输入邮箱！");
			return "recruiterPersonalInformation.html";
		}
		recruiter.setMailbox(mailbox);
		if (!headpic.isEmpty()) {
			String headpicname = headpic.getOriginalFilename();
			String suffix = headpicname.substring(headpicname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "recruiterPersonalInformation.html";
			}
			recruiter.setHeadpic(filesaveService.savePicture(USER_TYPR,recruiter.getUsername(), "headpic",suffix,headpic));
		}
		session.setAttribute("recruiter", recruiter);
		registerService.saveRecruiter(recruiter);
		return "recruiterPersonalInformation.html";
	}

	@RequestMapping("/recruiter/PersonalInformationLoad") // 用于加载招聘者的个人信息
	public String recruiterPersonalInformationLoad(@SessionAttribute(value = "recruiter") Recruiter recruiter,
			HttpSession session) {
		session.setAttribute("recruiter", recruiter);
		return "recruiterPersonalInformation.html";
	}

	@RequestMapping("/recruiter/IdentityLoad") // 用于加载招聘者的身份信息
	public String recruiterIdentityLoad(@SessionAttribute(value = "recruiter") Recruiter recruiter,
			HttpSession session) {
		session.setAttribute("recruiter", recruiter);
		return "recruiterIdentity.html";
	}

	@RequestMapping("/recruiter/PostLoad") // 用于加载招聘者的发布职位页面
	public String recruiterPostLoad(@SessionAttribute(value = "recruiter") Recruiter recruiter, HttpSession session) {
		ArrayList<Job> jobslist=complexSearchService.findJobsByRecruiterId(recruiter.getId());
		session.setAttribute("jobslist", jobslist);
		session.setAttribute("recruiter", recruiter);
		return "recruiterPost.html";
	}

	@RequestMapping("/recruiter/UpdatePassword") // 用于加载招聘者的更改密码页面
	public String recruiterUpdtePassword(@SessionAttribute(value = "recruiter") Recruiter recruiter,
			HttpSession session) {
		session.setAttribute("recruiter", recruiter);
		return "recruiterUpdatePassword.html";
	}

	@RequestMapping("/recruiter/SavePassword") // 用于保存招聘者的更改后的密码
	public String recruiterSavePassword(@SessionAttribute(value = "recruiter") Recruiter recruiter, Model m,
			String password1, String password2) {
		if (password1.equals("")) {
			m.addAttribute("message1", "请输入密码！");
			return "recruiterUpdatePassword.html";
		}
		if (password2.equals("")) {
			m.addAttribute("message2", "请输入确认密码！");
			return "recruiterUpdatePassword.html";
		}
		if (!password2.equals(password1)) {
			m.addAttribute("message2", "两次输入密码不一致！");
			return "recruiterUpdatePassword.html";
		}
		recruiter.setPassword(password1);
		registerService.saveRecruiter(recruiter);
		return "recruitersLogin.html";
	}
	
	@RequestMapping("/recruiter/login")
	public String recruiterLogin(String username, String password, Model m, HttpSession session) {
		session.setAttribute("username", username);
		if (username.equals("")) {
			m.addAttribute("message1", "请输入账号！");
			return "recruiterslogin.html";
		}
		if (password.equals("")) {
			m.addAttribute("message2", "请输入密码！");
			return "recruiterslogin.html";
		}
		ArrayList<Recruiter> gecruiterlist = loginService.findRecruiters(username);
		if (gecruiterlist.isEmpty()) {
			m.addAttribute("message1", "不存在此用户名！");
			return "recruiterslogin.html";
		}
		Recruiter recruiter = loginService.findRecruiter(username, password);
		if (recruiter == null) {
			m.addAttribute("message2", "密码错误！");
			return "recruiterslogin.html";
		}
		session.setAttribute("recruiter", recruiter);
		return "redirect:/recruiter/PostLoad";
	}

	@RequestMapping(value = "/recruiter/register", method = { RequestMethod.GET, RequestMethod.POST })
	public String recruiterRegister(String username, String password1, String password2, Model m,
			HttpSession session) {
		session.setAttribute("username", username);	
		if (username.equals("")) {
			m.addAttribute("message1", "请输入用户名！");
			return "recruitersregister.html";
		}
		if (password1.equals("")) {
			m.addAttribute("message2", "请输入密码！");
			return "recruitersregister.html";
		}
		if (password2.equals("")) {
			m.addAttribute("message3", "请输入确认密码！");
			return "recruitersregister.html";

		}
		if (!password1.equals(password2)) {
			m.addAttribute("message3", "两次输入密码不一致！");
			return "recruitersregister.html";
		}
		ArrayList<Recruiter> recruiterlist=registerService.findRecruiters(username);
		if(!recruiterlist.isEmpty()) {
			m.addAttribute("message1", "用户名已被注册！");
			return "recruitersregister.html";
		}
		Recruiter recruiter=new Recruiter();
		recruiter.setUsername(username);
		recruiter.setPassword(password1);
		registerService.saveRecruiter(recruiter);				
		return "recruiterslogin";
	}
}
