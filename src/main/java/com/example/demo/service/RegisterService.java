package com.example.demo.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.GraduateDao;
import com.example.demo.dao.JobDao;
import com.example.demo.dao.RecruiterDao;
import com.example.demo.dao.SchoolDao;
import com.example.demo.dao.SchoolmanDao;
import com.example.demo.entity.Graduate;
import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.School;
import com.example.demo.entity.Schoolman;

@Service
public class RegisterService {
	@Autowired
	SchoolDao schoolDao;	
	@Autowired
	SchoolmanDao schoolmanDao;
	@Autowired
	RecruiterDao recruiterDao;
	@Autowired
	GraduateDao graduateDao;
	@Autowired
	JobDao jobDao;
	
	
	public void saveJob(Job job) {
		jobDao.save(job);	
	}
	public void deleteJob(Integer id) {
		jobDao.deleteById(id);
	}
	
	public Job findAJobById(Integer id) {
		return jobDao.findJobById(id);	
	}

	public ArrayList<Schoolman> findSchoolmen(String username) {
		return schoolmanDao.findSchoolmenByUsername(username);	
	}
	
	public void saveSchoolman(Schoolman schoolman) {
		schoolmanDao.save(schoolman);	
	}
	
	public ArrayList<Recruiter> findRecruiters(String username) {
		return recruiterDao.findRecruitersByUsername(username);	
	}
	
	public void saveRecruiter(Recruiter recruiter) {
		recruiterDao.save(recruiter);	
	}
	
	public ArrayList<Graduate> findGraduates(String username) {
		return graduateDao.findGraduatesByUsername(username);
	}
	
	public void saveGraduate(Graduate graduate) {//保存毕业生对象
		graduateDao.save(graduate);	
	}
	
	public boolean querySchoolexist(String schoolname) {//查找学校是否存在
		ArrayList<School> schoollist=schoolDao.findSchoolBySchool(schoolname);
		if(schoollist.isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
}