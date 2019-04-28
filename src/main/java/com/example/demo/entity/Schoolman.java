package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schoolman")
public class Schoolman {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String name;
	@Column
	private String headpic;
	@Column
	private String school;
	@Column
	private String identity;
	@Column
	private String idcardp;
	@Column
	private String jobp;
	@Column
	private String yesorno;
	@Column
	private Date uptime;
	
	public Date getUptime() {
		return uptime;
	}
	public void setUptime(Date uptime) {
		this.uptime = uptime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}
	public String getIdcardp() {
		return idcardp;
	}
	public void setIdcardp(String idcardp) {
		this.idcardp = idcardp;
	}
	public String getJobp() {
		return jobp;
	}
	public void setJobp(String jobp) {
		this.jobp = jobp;
	}
	public String getYesorno() {
		return yesorno;
	}
	public void setYesorno(String yesorno) {
		this.yesorno = yesorno;
	}
	
	
	
	
}