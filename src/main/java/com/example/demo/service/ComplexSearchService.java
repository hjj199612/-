package com.example.demo.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.example.demo.dao.GraduateDao;
import com.example.demo.dao.JobDao;
import com.example.demo.dao.RecruiterDao;
import com.example.demo.dao.SchoolDao;
import com.example.demo.dao.SchoolmanDao;
import com.example.demo.entity.Job;
import com.example.demo.entity.Recruiter;
import com.example.demo.entity.Schoolman;

@Service
public class ComplexSearchService {
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
	
	public ArrayList<Job> findJobsByRecruiterId(Integer recruit) {	//查找招聘者待审核的列表
		Sort sort=new Sort(Direction.DESC, "uptime");//根据字段uptime进行desc升序排序
		return jobDao.findJobsByRecruit(recruit,sort);
	}
	
	public ArrayList<Recruiter> findRecruitersByUptimeIsNotNullAndYesornoIsNull() {	//查找招聘者待审核的列表
		Sort sort=new Sort(Direction.ASC, "uptime");//根据字段uptime进行asc升序排序
		return recruiterDao.findRecruitersByUptimeIsNotNullAndYesornoIsNull(sort);
	}
	
	public ArrayList<Schoolman> findSchoolmenByUptimeIsNotNullAndYesornoIsNull() {	//查找招聘者待审核的列表
		Sort sort=new Sort(Direction.ASC, "uptime");//根据字段uptime进行asc升序排序
		return schoolmanDao.findSchoolmenByUptimeIsNotNullAndYesornoIsNull(sort);
	}
}
