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
import com.example.demo.entity.Schoolman;
import com.example.demo.service.FilesaveService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;

@Controller
public class SchoolmanController {
	@Autowired
	LoginService loginService;
	@Autowired
	RegisterService registerService;
	@Autowired
	FilesaveService filesaveService;
	final String  USER_TYPR="schoolmen";

	@RequestMapping(value="/schoolman/Identity",method = { RequestMethod.GET, RequestMethod.POST }) // 用于保存新建的公司的验证照片
	public String recruiterIdentityStep3(@SessionAttribute(value = "schoolman") Schoolman schoolman,@RequestParam(value = "jobp") MultipartFile jobp,
			@RequestParam(value = "idcardp") MultipartFile idcardp, Model m, HttpSession session) throws ServletException, IOException {
		
		if (!jobp.isEmpty()) {
			String jobpname = jobp.getOriginalFilename();
			String suffix = jobpname.substring(jobpname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "schoolmanIdentity.html";
			}
			schoolman.setJobp(filesaveService.savePicture(USER_TYPR,schoolman.getUsername(), "jobp",suffix,jobp));
		}else {
			m.addAttribute("message1", "请选择公司营业执照！");
			return "schoolmanIdentity.html";
		}
		
		if (!idcardp.isEmpty()) {
			String idcardpname = idcardp.getOriginalFilename();
			String suffix = idcardpname.substring(idcardpname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message2", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "schoolmanIdentity.html";
			}
			schoolman.setIdcardp(filesaveService.savePicture(USER_TYPR,schoolman.getUsername(), "idcardp",suffix,idcardp));
		}else {
			m.addAttribute("message1", "请选择公司营业执照！");
			return "schoolmanIdentity.html";
		}
		schoolman.setYesorno(null);
		Date now=new Date();
		schoolman.setUptime(now);
		session.setAttribute("schoolman", schoolman);
		registerService.saveSchoolman(schoolman);
		return "schoolmanIdentity.html";
	}
	
	@RequestMapping(value="/schoolman/updatePersonalInformation" ,method = { RequestMethod.GET, RequestMethod.POST })// 用于保存修改后的个人信息
	public String recruiterupdatePersonalInformation(@SessionAttribute(value = "schoolman") Schoolman schoolman,
			@RequestParam(value = "headpic") MultipartFile headpic, String name, String school, String identity,
			Model m, HttpSession session, HttpServletRequest request)
			throws ServletException, IOException {
		if (name.equals("")) {
			m.addAttribute("message2", "请输入姓名！");
			return "schoolmanPersonalInformation.html";
		}
		schoolman.setName(name);
		if (school.equals("")) {
			m.addAttribute("message3", "请输入学校！");
			return "schoolmanPersonalInformation.html";
		}
		if(!registerService.querySchoolexist(school)) {
			m.addAttribute("message3", "不存在该学校！");
			return "schoolmanPersonalInformation.html";
		}
		schoolman.setSchool(school);
		if (identity.equals("")) {
			m.addAttribute("message4", "请输入职位！");
			return "schoolmanPersonalInformation.html";
		}
		schoolman.setIdentity(identity);
		if (!headpic.isEmpty()) {
			String headpicname = headpic.getOriginalFilename();
			String suffix = headpicname.substring(headpicname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "schoolmanPersonalInformation.html";
			}
			schoolman.setHeadpic(filesaveService.savePicture(USER_TYPR,schoolman.getUsername(), "headpic",suffix,headpic));
		}
		schoolman.setYesorno(null);
		session.setAttribute("schoolman", schoolman);
		registerService.saveSchoolman(schoolman);
		return "schoolmanPersonalInformation.html";
	}

	@RequestMapping("/schoolman/PersonalInformationLoad") // 用于加载招聘者的个人信息
	public String schoolmanPersonalInformationLoad(@SessionAttribute(value = "schoolman") Schoolman schoolman,
			HttpSession session) {
		session.setAttribute("schoolman", schoolman);
		return "schoolmanPersonalInformation.html";
	}			

	@RequestMapping("/schoolman/IdentityLoad") // 用于加载招聘者的身份信息
	public String schoolmanIdentityLoad(@SessionAttribute(value = "schoolman") Schoolman schoolman,
			HttpSession session) {
		session.setAttribute("schoolman", schoolman);
		return "schoolmanIdentity.html";
	}



	@RequestMapping("/schoolman/UpdatePassword") // 用于加载招聘者的更改密码页面
	public String schoolmanUpdtePassword(@SessionAttribute(value = "schoolman") Schoolman schoolman,
			HttpSession session) {
		session.setAttribute("schoolman", schoolman);
		return "schoolmanUpdatePassword.html";
	}

	@RequestMapping("/schoolman/SavePassword") // 用于保存招聘者的更改后的密码
	public String schoolmanSavePassword(@SessionAttribute(value = "schoolman") Schoolman schoolman, Model m,
			String password1, String password2) {
		if (password1.equals("")) {
			m.addAttribute("message1", "请输入密码！");
			return "schoolmanUpdatePassword.html";
		}
		if (password2.equals("")) {
			m.addAttribute("message2", "请输入确认密码！");
			return "schoolmanUpdatePassword.html";
		}
		if (!password2.equals(password1)) {
			m.addAttribute("message2", "两次输入密码不一致！");
			return "schoolmanUpdatePassword.html";
		}
		schoolman.setPassword(password1);
		registerService.saveSchoolman(schoolman);
		return "redirect:/schoolmenLogin";
	}
	
	@RequestMapping("/schoolman/Login")
	public String schoolmanlogin(String username, String password, Model m, HttpSession session) {
		session.setAttribute("username", username);
		if (username.equals("")) {
			m.addAttribute("message1", "请输入账号！");
			return "schoolmenlogin.html";
		}
		if (password.equals("")) {
			m.addAttribute("message2", "请输入密码！");
			return "schoolmenlogin.html";
		}
		ArrayList<Schoolman> schoolmenlist = loginService.findSchoolmen(username);
		if (schoolmenlist.isEmpty()) {
			m.addAttribute("message1", "不存在此用户名！");
			return "schoolmenlogin.html";
		}
		Schoolman schoolman = loginService.findSchoolman(username, password);
		if (schoolman == null) {
			m.addAttribute("message2", "密码错误！");
			return "schoolmenlogin.html";
		}
		session.setAttribute("schoolman", schoolman);
		return "redirect:/schoolman/CheckGraduatesLoad";
	}

	@RequestMapping(value = "/schoolman/Register", method = { RequestMethod.GET, RequestMethod.POST })
	public String schoolmanRegister(String username, String password1, String password2, 
			@RequestParam(value = "headpic") MultipartFile headpic, Model m,
			HttpSession session) throws ServletException, IOException {
		Schoolman schoolman = new Schoolman();
		if (username.equals("")) {
			m.addAttribute("message2", "请输入用户名！");
			return "schoolmenregister.html";
		}
		session.setAttribute("username", username);
		schoolman.setUsername(username);
		if (password1.equals("")) {
			m.addAttribute("message3", "请输入密码！");
			return "schoolmenregister.html";
		}
		if (password2.equals("")) {
			m.addAttribute("message4", "请输入确认密码！");
			return "schoolmenregister.html";
		}
		if (!password1.equals(password2)) {
			m.addAttribute("message4", "两次输入密码不一致！");
			return "schoolmenregister.html";
		}
		schoolman.setPassword(password1);
		//验证是否已存在用户名
		ArrayList<Schoolman> schoolmenlist=registerService.findSchoolmen(username);
		if(!schoolmenlist.isEmpty()) {
			m.addAttribute("message2", "用户名已被注册！");
			return "schoolmenregister.html";
		}	
			if (!headpic.isEmpty()) {
				String headpicname = headpic.getOriginalFilename();
				String suffix = headpicname.substring(headpicname.lastIndexOf(".") + 1);
				if (!suffix.equals("png") && !suffix.equals("jpg")) {
					m.addAttribute("message1", "只接受png或jpg格式的图片！");
					System.out.println(suffix);
					return "schoolmenregister.html";
				}
				schoolman.setHeadpic(filesaveService.savePicture(USER_TYPR,schoolman.getUsername(), "headpic",suffix,headpic));
			}
			else {
				m.addAttribute("message1", "请选择头像！");
				return "schoolmenregister.html";
			}
			registerService.saveSchoolman(schoolman);		
		    session.setAttribute("schoolman",schoolman);
		return "schoolmenlogin";
	}
}
