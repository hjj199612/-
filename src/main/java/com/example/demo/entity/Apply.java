package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="apply")
public class Apply {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column
	private Integer graduateid;
	@Column
	private Integer jobid;
	@Column
	private Integer recruit;
	@Column
	private Date uptime;
	@Column
	private String refuse;
	@Transient//加上这个字段就可以不与数据库表进行映射
	private Job job;
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getRefuse() {
		return refuse;
	}
	public void setRefuse(String refuse) {
		this.refuse = refuse;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getGraduateid() {
		return graduateid;
	}
	public void setGraduateid(Integer graduateid) {
		this.graduateid = graduateid;
	}
	public Integer getJobid() {
		return jobid;
	}
	public void setJobid(Integer jobid) {
		this.jobid = jobid;
	}
	public Integer getRecruit() {
		return recruit;
	}
	public void setRecruit(Integer recruit) {
		this.recruit = recruit;
	}
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}

	
}