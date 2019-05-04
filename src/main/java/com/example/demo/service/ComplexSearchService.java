package com.example.demo.service;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import com.example.demo.dao.ApplyDao;
import com.example.demo.dao.GraduateDao;
import com.example.demo.dao.JobDao;
import com.example.demo.dao.RecruiterDao;
import com.example.demo.dao.SchoolDao;
import com.example.demo.dao.SchoolmanDao;
import com.example.demo.entity.Apply;
import com.example.demo.entity.Graduate;
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
	@Autowired
	ApplyDao applyDao;

	
	public Page<Job> getJob(int pageNumber,int pageSize){
		 Sort sort = new Sort(Sort.Direction.DESC,"uptime"); //创建时间降序排序
		 Pageable pageable = new PageRequest(pageNumber,pageSize,sort);
		 return jobDao.findAll(pageable);
    }

	
	public ArrayList<Graduate> findGraduatesBySchool(String school,String sort1,String sort2) {
		if(sort2.equals("DESC")) {
			Sort sort = new Sort(Direction.DESC, sort1);// 根据字段studentid进行desc升序排序
			return graduateDao.findGraduatesBySchool(school, sort);
		}
		Sort sort = new Sort(Direction.ASC, sort1);// 根据字段studentid进行desc升序排序
		return graduateDao.findGraduatesBySchool(school, sort);
	}
	public ArrayList<Integer> findAllByJobidAndRefuseIsNull(Integer jobid) {
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return applyDao.findAllByJobidAndRefuseIsNull(jobid, sort);
	}
	
	public ArrayList<Integer> findAllByJobid(Integer jobid) {
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return applyDao.findAllByJobid(jobid, sort);
	}
	
	public ArrayList<Integer> findAllByRecruit(Integer recruit) {
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return applyDao.findAllByRecruit(recruit, sort);
	}

	public ArrayList<Apply> findAllByGraduateid(Integer graduateid) {// 查找职位
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return applyDao.findAllByGraduateid(graduateid, sort);
	}

	public boolean canApplyThisJob(Integer graduateid, Integer jobid) {
		if (applyDao.findApplyListByGraduateidAndJobid(graduateid, jobid).isEmpty()) {
			return true;
		}
		return false;
	}

	public ArrayList<Job> getJobListByInform(String inform) {// 毕业生查找职位
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return jobDao.findJobsByNameLikeOrCityLike("%" + inform + "%", "%" + inform + "%", sort);
	}

	public ArrayList<Job> getJobListWhenGraduateLogin() {// 毕业生登录时给出一些职位
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return (ArrayList<Job>) jobDao.findAll(sort);
	}

	public ArrayList<Job> findJobsByRecruiterId(Integer recruit) { // 查找招聘者待审核的列表
		Sort sort = new Sort(Direction.DESC, "uptime");// 根据字段uptime进行desc升序排序
		return jobDao.findJobsByRecruit(recruit, sort);
	}

	public ArrayList<Recruiter> findRecruitersByUptimeIsNotNullAndYesornoIsNull() { // 查找招聘者待审核的列表
		Sort sort = new Sort(Direction.ASC, "uptime");// 根据字段uptime进行asc升序排序
		return recruiterDao.findRecruitersByUptimeIsNotNullAndYesornoIsNull(sort);
	}

	public ArrayList<Schoolman> findSchoolmenByUptimeIsNotNullAndYesornoIsNull() { // 查找招聘者待审核的列表
		Sort sort = new Sort(Direction.ASC, "uptime");// 根据字段uptime进行asc升序排序
		return schoolmanDao.findSchoolmenByUptimeIsNotNullAndYesornoIsNull(sort);
	}

}
