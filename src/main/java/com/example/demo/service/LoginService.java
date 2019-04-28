package com.example.demo.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.AdminDao;
import com.example.demo.dao.GraduateDao;
import com.example.demo.dao.RecruiterDao;
import com.example.demo.dao.SchoolmanDao;
import com.example.demo.entity.Admin;
import com.example.demo.entity.Graduate;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.Schoolman;

@Service
public class LoginService {
	@Autowired
	AdminDao adminDao;
	@Autowired
	SchoolmanDao schoolmanDao;
	@Autowired
	RecruiterDao recruiterDao;
	@Autowired
	GraduateDao graduateDao;
	
	public Admin findAdmin(String account, String password) {
		return adminDao.findByAccountAndPassword(account, password);
	}
	
	public Schoolman findSchoolman(String username,String password) {
		return schoolmanDao.findByUsernameAndPassword(username, password);
	}
	
	public Schoolman findSchoolmanById(Integer id) {
		return schoolmanDao.findSchoolmanById(id);
	}
	
	public Recruiter findRecruiter(String username,String password) {
		return recruiterDao.findByUsernameAndPassword(username, password);
	}
	
	public Recruiter findRecruiterById(Integer id) {
		return recruiterDao.findRecruiterById(id);
	}
	
	public Graduate findGraduate(String username,String password) {
		return graduateDao.findByUsernameAndPassword(username, password);
	}
	
	public ArrayList<Schoolman> findSchoolmen(String username) {
		return schoolmanDao.findSchoolmenByUsername(username);	
	}
	
	public ArrayList<Recruiter> findRecruiters(String username) {
		return recruiterDao.findRecruitersByUsername(username);
	}
	
	public ArrayList<Graduate> findGraduates(String username) {
		return graduateDao.findGraduatesByUsername(username);	
	}
	
	//

}