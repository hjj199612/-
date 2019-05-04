package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.Apply;
import com.example.demo.entity.Graduate;
import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.Schoolman;
import com.example.demo.service.ComplexSearchService;
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
	@Autowired
	ComplexSearchService complexSearchService;
	final String  USER_TYPR="graduate";
	
	@RequestMapping(value="/graduate/Apply",method = { RequestMethod.GET, RequestMethod.POST })//下载简历文件
	public String graduateApply(@SessionAttribute(value = "graduate") Graduate graduate,HttpSession session){
		ArrayList<Apply> applylist=complexSearchService.findAllByGraduateid(graduate.getId());
		for(Apply apply:applylist) {
			apply.setJob(registerService.findAJobById(apply.getJobid()));
		}
		session.setAttribute("applylist", applylist);
		session.setAttribute("graduate", graduate);
		return "graduateApply.html";
	}
	
	@RequestMapping(value="/graduate/ApplyJob",method = { RequestMethod.GET, RequestMethod.POST })//下载简历文件
	public String graduateApplicationJob(@SessionAttribute(value = "graduate") Graduate graduate,String recruit,String jobId,HttpSession session) throws ServletException, IOException {
		if(graduate.getResume()==null) {
			return "graduateResume.html";
		}
		if(graduate.getName()==null) {
			return "graduateIdentity.html";
		}
		if(complexSearchService.canApplyThisJob(graduate.getId(), Integer.valueOf(jobId))) {
			Apply apply=new Apply();
			apply.setRecruit(Integer.valueOf(recruit));
			apply.setJobid(Integer.valueOf(jobId));
			apply.setGraduateid(graduate.getId());
			Date now=new Date();
			apply.setUptime(now);
			registerService.saveApply(apply);
		}
		session.setAttribute("graduate", graduate);
		return "graduates.html";
	}
	
	@RequestMapping(value="/graduate/ResumeGet",method = { RequestMethod.GET, RequestMethod.POST })//下载简历文件
	public String graduateResumeGet(@SessionAttribute(value = "graduate") Graduate graduate,HttpSession session,HttpServletResponse response) throws ServletException, IOException {
		filesaveService.getResume(graduate.getResume(),graduate.getResumename(), response);
		session.setAttribute("graduate", graduate);
		return "graduateResume.html";
	}
	
	@RequestMapping(value="/graduate/ResumeUpLoad",method = { RequestMethod.GET, RequestMethod.POST })
	public String graduateResumeUpLoad(@SessionAttribute(value = "graduate") Graduate graduate,HttpSession session,
			Model m,String resumename,MultipartFile resume) throws ServletException, IOException {//上传简历信息
		if (!resume.isEmpty()) {
			String resumeName = resume.getOriginalFilename();
			String suffix = resumeName.substring(resumeName.lastIndexOf(".") + 1);
			if(suffix.equals("doc") || suffix.equals("docx")||suffix.equals("pdf")||suffix.equals("ppt")) {
				graduate.setResume(filesaveService.savePicture(USER_TYPR,graduate.getUsername(), "resume",suffix,resume));//保存简历
			}
			else{
				m.addAttribute("message1", "不接受"+suffix+"格式的图片！");
				return "graduateResume.html";
			}
		}
		graduate.setResumename(resumename);
		registerService.saveGraduate(graduate);				
		session.setAttribute("graduate", graduate);
		return "graduateResume.html";
	}
	
	@RequestMapping(value="/graduate/IdentitySave",method = { RequestMethod.GET, RequestMethod.POST }) // 用于保存新建的公司
	public String recruiterIdentityStep2(@SessionAttribute(value = "graduate") Graduate graduate,@RequestParam(value = "headpic") MultipartFile headpic,
			String name, String sex, String birth, String school,String college,String education,String major,String studentid,String phone,String email,String expectedwork,
			String state, String company,String majorrelevant,Model m, HttpSession session) throws ServletException, IOException {
		
		graduate.setName(name);
		graduate.setSex(sex);
		graduate.setBirth(birth);
		graduate.setSchool(school);
		graduate.setCollege(college);
		graduate.setEducation(education);
		graduate.setMajor(major);
		graduate.setStudentid(studentid);
		graduate.setPhone(phone);
		graduate.setEmail(email);
		graduate.setState(Integer.valueOf(state));
		graduate.setCompany(company);
		graduate.setMajorrelevant(Integer.valueOf(majorrelevant));
		graduate.setExpectedwork(expectedwork);
		session.setAttribute("graduate", graduate);
		if(!registerService.querySchoolexist(school)) {
			m.addAttribute("message2", "不存在该学校！");
			return "graduateIdentity.html";
		}
		if (!headpic.isEmpty()) {
			String headpicname = headpic.getOriginalFilename();
			String suffix = headpicname.substring(headpicname.lastIndexOf(".") + 1);
			if (!suffix.equals("png") && !suffix.equals("jpg")) {
				m.addAttribute("message1", "只接受png或jpg格式的图片！");
				System.out.println(suffix);
				return "graduateIdentity.html";
			}
			graduate.setHeadpic(filesaveService.savePicture(USER_TYPR,graduate.getUsername(), "headpic",suffix,headpic));
		}
		session.setAttribute("graduate", graduate);
		registerService.saveGraduate(graduate);				
		return "redirect:/graduate/LoadJobs";
	}
	
	@RequestMapping(value="/graduate/ResumeLoad",method = { RequestMethod.GET, RequestMethod.POST })
	public String graduateResumeLoad(@SessionAttribute(value = "graduate") Graduate graduate,HttpSession session) {//加载个人信息
		session.setAttribute("graduate", graduate);
		return "graduateResume.html";
	}
	
	@RequestMapping(value="/graduate/IdentityLoad",method = { RequestMethod.GET, RequestMethod.POST })
	public String graduateIdentityLoad(@SessionAttribute(value = "graduate") Graduate graduate,HttpSession session) {//加载个人信息
		session.setAttribute("graduate", graduate);
		return "graduateIdentity.html";
	}
	
	@RequestMapping(value="/graduate/CheckJob",method = { RequestMethod.GET, RequestMethod.POST }) // 跳转到发布职位的界面
	public String graduateCheckJob(@SessionAttribute(value = "graduate") Graduate graduate,String id,HttpSession session)  {
		Job job=registerService.findAJobById(Integer.valueOf(id));
		Recruiter recruiter=registerService.findRecruiterById(job.getRecruit());
		session.setAttribute("job", job);
		session.setAttribute("recruiter", recruiter);
		session.setAttribute("graduate", graduate);
		return "graduateCheckJob.html";
	}
	
	@RequestMapping("/graduate/QueryJobs") // 用于毕业生查找工作
	public String graduateQueryJobs(@SessionAttribute(value = "graduate") Graduate graduate,String inform,HttpSession session) {
		ArrayList<Job> joblist=complexSearchService.getJobListByInform("%"+inform+"%");
		session.setAttribute("joblist", joblist);
		session.setAttribute("graduate", graduate);
		return "graduates.html";
	}
	
	@RequestMapping("/graduate/LoadJobs") // 用于毕业生查找工作
	public String graduateLoadJobs(@SessionAttribute(value = "graduate") Graduate graduate,HttpSession session) {
		
		ArrayList<Job> joblist=complexSearchService.getJobListWhenGraduateLogin();
		session.setAttribute("joblist", joblist);
		session.setAttribute("graduate", graduate);
		return "graduates.html";
	}
	
	@RequestMapping("/graduate/SavePassword") // 用于保存招聘者的更改后的密码
	public String graduateSavePassword(@SessionAttribute(value = "graduate") Graduate graduate, Model m,
			String password1, String password2) {
		if (password1.equals("")) {
			m.addAttribute("message1", "请输入密码！");
			return "graduateUpdatePassword.html";
		}
		if (password2.equals("")) {
			m.addAttribute("message2", "请输入确认密码！");
			return "graduateUpdatePassword.html";
		}
		if (!password2.equals(password1)) {
			m.addAttribute("message2", "两次输入密码不一致！");
			return "graduateUpdatePassword.html";
		}
		graduate.setPassword(password1);
		registerService.saveGraduate(graduate);
		return "redirect:/graduatesLogin";
	}
	
	@RequestMapping("/graduate/UpdatePassword") // 用于加载毕业生的更改密码页面
	public String graduateUpdtePassword(@SessionAttribute(value = "graduate") Graduate graduate,
			HttpSession session) {
		session.setAttribute("recruiter", graduate);
		return "graduateUpdatePassword.html";
	}
	
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
		ArrayList<Graduate> graduatelist = loginService.findGraduates(username);
		if (graduatelist.isEmpty()) {
			m.addAttribute("message1", "不存在此用户名！");
			return "graduateslogin.html";
		}
		Graduate graduate = loginService.findGraduate(username, password);
		if (graduate == null) {
			m.addAttribute("message2", "密码错误！");
			return "graduateslogin.html";
		}
		session.setAttribute("graduate", graduate);
		return "redirect:/graduate/LoadJobs";
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
