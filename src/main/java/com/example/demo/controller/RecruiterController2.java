package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.demo.entity.Graduate;
import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.service.ComplexSearchService;
import com.example.demo.service.FilesaveService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;

@Controller
public class RecruiterController2 {
	@Autowired
	LoginService loginService;
	@Autowired
	RegisterService registerService;
	@Autowired
	FilesaveService filesaveService;
	@Autowired
	ComplexSearchService complexSearchService;
	final String  USER_TYPR="recruiters";
	
	@RequestMapping(value="/recruiter/Refuse",method = { RequestMethod.GET, RequestMethod.POST })//下载简历文件
	public String recruiterRefuse(@SessionAttribute(value = "recruiter") Recruiter recruiter,
			@SessionAttribute(value = "job") Job job,String graduateid,HttpSession session) {
		registerService.refuseApplyByGraduateidAndJobid(Integer.valueOf(graduateid),job.getId());
		ArrayList<Graduate> graduatelist=new ArrayList<Graduate>();
		ArrayList<Integer> garaduateidList=complexSearchService.findAllByJobidAndRefuseIsNull(job.getId());
		for(Integer i:garaduateidList) {
			graduatelist.add(registerService.findAGraduateById(i));
		}
		session.setAttribute("recruiter", recruiter);
		session.setAttribute("job", job);
		session.setAttribute("graduatelist", graduatelist);
		return "recruiterCheckApply.html";
	}
	
	@RequestMapping(value="/recruiter/ResumeGet",method = { RequestMethod.GET, RequestMethod.POST })//下载简历文件
	public String recruiterResumeGet(@SessionAttribute(value = "recruiter") Recruiter recruiter,@SessionAttribute(value = "graduatelist") ArrayList<Graduate> graduatelist,
			String resume,String resumename,HttpSession session,HttpServletResponse response) throws ServletException, IOException {
		filesaveService.getResume(resume,resumename, response);
		session.setAttribute("recruiter", recruiter);
		session.setAttribute("graduatelist", graduatelist);
		return "recruiterCheckApply.html";
	}
	
	@RequestMapping(value="/recruiter/CheckApply",method = { RequestMethod.GET, RequestMethod.POST }) // 删除职位
	public String recruiterCheckApply(@SessionAttribute(value = "recruiter") Recruiter recruiter,String jobid,HttpSession session)  {
		ArrayList<Graduate> graduatelist=new ArrayList<Graduate>();
		ArrayList<Integer> garaduateidList=complexSearchService.findAllByJobidAndRefuseIsNull(Integer.valueOf(jobid));
		for(Integer i:garaduateidList) {
			graduatelist.add(registerService.findAGraduateById(i));
		}
		session.setAttribute("job", registerService.findAJobById(Integer.valueOf(jobid)));
		session.setAttribute("graduatelist", graduatelist);
		session.setAttribute("recruiter", recruiter);
		return "recruiterCheckApply.html";
	}
	
	@RequestMapping(value="/recruiter/ApplyLoad",method = { RequestMethod.GET, RequestMethod.POST }) // 删除职位
	public String recruiterApplyLoad(@SessionAttribute(value = "recruiter") Recruiter recruiter,HttpSession session)  {
		ArrayList<Job> joblist=new ArrayList<Job>();
		ArrayList<Integer> jobidList=complexSearchService.findAllByRecruit(recruiter.getId());
		for(Integer i:jobidList) {
			joblist.add(registerService.findAJobById(i));
		}
		session.setAttribute("joblist", joblist);
		session.setAttribute("recruiter", recruiter);
		return "recruiterApplys.html";
	}
	
	@RequestMapping(value="/recruiter/DeleteTheJob",method = { RequestMethod.GET, RequestMethod.POST }) // 删除职位
	public String recruiterDeleteTheJob(@SessionAttribute(value = "recruiter") Recruiter recruiter,String id,HttpSession session)  {
		registerService.deleteJob(Integer.valueOf(id));
		session.setAttribute("recruiter", recruiter);
		return "redirect:/recruiter/PostLoad";
	}
	
	@RequestMapping(value="/recruiter/ChangeTheJob",method = { RequestMethod.GET, RequestMethod.POST }) // 跳转到发布职位的界面
	public String recruiterPostChangeTheJob(@SessionAttribute(value = "job") Job job,@SessionAttribute(value = "recruiter") Recruiter recruiter,String name,String experience,String education,String nature,String salary,String city,
			String address,String describes,HttpSession session)  {
		session.setAttribute("recruiter", recruiter);	
		session.setAttribute("name", name);
		session.setAttribute("experience", experience);	
		session.setAttribute("education", education);	
		session.setAttribute("nature", nature);	
		session.setAttribute("salary", salary);	
		session.setAttribute("city", city);	
		session.setAttribute("address", address);	
		session.setAttribute("describe", describes);	
		if (name.equals("")) {
			return "recruiterPostJob.html";
		}
		if (experience.equals("")) {
			return "recruiterPostJob.html";
		}
		if (education.equals("")) {
			return "recruiterPostJob.html";
		}
		if (nature.equals("")) {
			return "recruiterPostJob.html";
		}
		if (salary.equals("")) {
			return "recruiterPostJob.html";
		}
		if (city.equals("")) {
			return "recruiterPostJob.html";
		}
		if (address.equals("")) {
			return "recruiterPostJob.html";
		}
		if (describes.equals("")) {
			return "recruiterPostJob.html";
		}
		job.setName(name);
		job.setExperience(experience);
		job.setEducation(education);
		job.setNature(nature);
		job.setSalary(salary);
		job.setCity(city);
		job.setAddress(address);
		job.setDescribes(describes);
		job.setRecruit(recruiter.getId());
		Date now=new Date();
		job.setUptime(now);//设置上传时间
		registerService.saveJob(job);				
		return "redirect:/recruiter/PostLoad";
	}
	
	@RequestMapping(value="/recruiter/PostChangeJob",method = { RequestMethod.GET, RequestMethod.POST }) // 跳转到发布职位的界面
	public String recruiterPostChangeJob(@SessionAttribute(value = "recruiter") Recruiter recruiter,String id,HttpSession session)  {
		Job job=registerService.findAJobById(Integer.valueOf(id));
		session.setAttribute("job", job);
		session.setAttribute("recruiter", recruiter);
		return "recruiterPostChangeJob.html";
	}
	
	@RequestMapping(value="/recruiter/PostJob",method = { RequestMethod.GET, RequestMethod.POST }) // 跳转到发布职位的界面
	public String recruiterPostJob(@SessionAttribute(value = "recruiter") Recruiter recruiter,HttpSession session)  {
		session.setAttribute("recruiter", recruiter);
		return "recruiterPostJob.html";
	}
	@RequestMapping(value="/recruiter/PostAJob",method = { RequestMethod.GET, RequestMethod.POST }) // 用于保存新建的公司的验证照片
	public String recruiterPostAJob(@SessionAttribute(value = "recruiter") Recruiter recruiter,String name,String experience,String education,String nature,String salary,String city,
			String address,String describes,HttpSession session)  {
		session.setAttribute("recruiter", recruiter);	
		session.setAttribute("name", name);
		session.setAttribute("experience", experience);	
		session.setAttribute("education", education);	
		session.setAttribute("nature", nature);	
		session.setAttribute("salary", salary);	
		session.setAttribute("city", city);	
		session.setAttribute("address", address);	
		session.setAttribute("describe", describes);	
		if (name.equals("")) {
			return "recruiterPostJob.html";
		}
		if (experience.equals("")) {
			return "recruiterPostJob.html";
		}
		if (education.equals("")) {
			return "recruiterPostJob.html";
		}
		if (nature.equals("")) {
			return "recruiterPostJob.html";
		}
		if (salary.equals("")) {
			return "recruiterPostJob.html";
		}
		if (city.equals("")) {
			return "recruiterPostJob.html";
		}
		if (address.equals("")) {
			return "recruiterPostJob.html";
		}
		if (describes.equals("")) {
			return "recruiterPostJob.html";
		}
		Job job=new Job();
		job.setName(name);
		job.setExperience(experience);
		job.setEducation(education);
		job.setNature(nature);
		job.setSalary(salary);
		job.setCity(city);
		job.setAddress(address);
		job.setDescribes(describes);
		job.setRecruit(recruiter.getId());
		Date now=new Date();
		job.setUptime(now);//设置上传时间
		registerService.saveJob(job);				
		return "redirect:/recruiter/PostLoad";
	}
}
