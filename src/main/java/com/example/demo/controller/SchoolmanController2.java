package com.example.demo.controller;

import java.io.IOException;
import java.text.DecimalFormat;
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

import com.example.demo.entity.Graduate;
import com.example.demo.entity.Schoolman;
import com.example.demo.service.ComplexSearchService;
import com.example.demo.service.FilesaveService;
import com.example.demo.service.LoginService;
import com.example.demo.service.RegisterService;

@Controller
public class SchoolmanController2 {
	@Autowired
	LoginService loginService;
	@Autowired
	RegisterService registerService;
	@Autowired
	FilesaveService filesaveService;
	@Autowired
	ComplexSearchService complexSearchService;
	final String  USER_TYPR="schoolmen";

	@RequestMapping("/schoolman/CheckGraduatesByInform") // 用于加载招聘者的身份信息
	public String schoolmanCheckGraduatesByInform(@SessionAttribute(value = "schoolman") Schoolman schoolman,String college,
			String education,String major,String state,String majorrelevant,String sort1,String sort2,HttpSession session,Model m) {
		if(sort1==null) {
			sort1="studentid";
		}
		if(sort2==null) {
			sort2="DESC";
		}
		int workingCount=0;//就业人数
		int workingAndMajor=0;//专业相关人数
		ArrayList<Graduate> graduatelist0=complexSearchService.findGraduatesBySchool(schoolman.getSchool(),sort1,sort2);
		ArrayList<Graduate> graduatelist=new ArrayList<Graduate>();
		for(Graduate g:graduatelist0) {
			if(!college.equals("")&&!college.equals(g.getCollege())) {
				continue;
			}
			if(!education.equals("")&&!education.equals(g.getEducation())) {
				continue;
			}
			if(!major.equals("")&&!major.equals(g.getMajor())) {
				continue;
			}
			if(!state.equals("")&&!state.equals(g.getState().toString())) {
				continue;
			}
			if(majorrelevant!=null&&!majorrelevant.equals(g.getMajorrelevant().toString())) {
				continue;
			}
			if(majorrelevant!=null&&majorrelevant.equals("0")&&g.getState()==0) {
				continue;
			}
			if(majorrelevant!=null&&majorrelevant.equals("1")&&g.getState()==0) {
				continue;
			}
			if(g.getState()!=null&&g.getState()==1) {
				workingCount++;
				if(g.getMajorrelevant()!=null&&g.getMajorrelevant()==1) {
					workingAndMajor++;
				}
			}
			graduatelist.add(g);
		}
		float jiuyelv= (float)workingCount/(float)graduatelist.size();//就业率
		float jiuyelv2= (float)workingAndMajor/(float)workingCount;//专业相关的概率
		DecimalFormat df = new DecimalFormat("00.0");//格式化小数 
		String message0;
		if("0".equals(majorrelevant)) {
			message0="已就业人数："+workingCount+"人,就业率："+df.format(jiuyelv*100)+"%,其中"+workingAndMajor+
					"人从事专业无关的工作,占就业人数的"+df.format(100-jiuyelv2*100)+"%";
		}else {
			message0="已就业人数："+workingCount+"人,就业率："+df.format(jiuyelv*100)+"%,其中"+workingAndMajor+
					"人从事专业相关的工作,占就业人数的"+df.format(jiuyelv2*100)+"%";
		}
		m.addAttribute("message0", message0);
		if(state.equals("0")) {
			m.addAttribute("message0", "未就职的人数："+graduatelist.size());
		}
		String message="";
		if(!college.equals("")) {
			message+=college+" ";
		}
		if(!education.equals("")) {
			message+=education+"生 ";
		}
		if(!major.equals("")) {
			message+=major+"专业 ";
		}
		if(state.equals("1")) {
			message+="已就职 ";
		}
		if(state.equals("0")) {
			message+="未就职 ";
		}
		message+="学生信息如下：";
		m.addAttribute("message", message);
		session.setAttribute("graduatelist", graduatelist);
		session.setAttribute("schoolman", schoolman);
		return "schoolmanCheckGraduates.html";
	}
	
	@RequestMapping("/schoolman/CheckGraduatesLoad") // 用于加载招聘者的身份信息
	public String schoolmanCheckGraduatesLoad(@SessionAttribute(value = "schoolman") Schoolman schoolman,
			HttpSession session) {
		session.setAttribute("schoolman", schoolman);
		return "schoolmanCheckGraduates.html";
	}
}
