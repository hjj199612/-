package com.example.demo.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recruiter")
public class Recruiter {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column
	private String username;
	@Column
	private String password;
	@Column
	private String headpic;
	@Column
	private String name;
	@Column
	private String called;
	@Column
	private String position;
	@Column
	private String department;
	@Column
	private String phone;
	@Column
	private String mailbox;
	@Column
	private String companyname;
	@Column
	private String companyshortname;
	@Column
	private String companylogo;
	@Column
	private String companyfield;
	@Column
	private String companyscale;
	@Column
	private String	companydevelop;
	@Column
	private String identitypicture;
	@Column
	private String companypicture;
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
	public String getCompanyshortname() {
		return companyshortname;
	}
	public void setCompanyshortname(String companyshortname) {
		this.companyshortname = companyshortname;
	}
	public String getCompanylogo() {
		return companylogo;
	}
	public void setCompanylogo(String companylogo) {
		this.companylogo = companylogo;
	}
	public String getCompanyfield() {
		return companyfield;
	}
	public void setCompanyfield(String companyfield) {
		this.companyfield = companyfield;
	}
	public String getCompanyscale() {
		return companyscale;
	}
	public void setCompanyscale(String companyscale) {
		this.companyscale = companyscale;
	}
	public String getCompanydevelop() {
		return companydevelop;
	}
	public void setCompanydevelop(String companydevelop) {
		this.companydevelop = companydevelop;
	}
	public String getIdentitypicture() {
		return identitypicture;
	}
	public void setIdentitypicture(String identitypicture) {
		this.identitypicture = identitypicture;
	}
	public String getCompanypicture() {
		return companypicture;
	}
	public void setCompanypicture(String companypicture) {
		this.companypicture = companypicture;
	}
	public String getYesorno() {
		return yesorno;
	}
	public void setYesorno(String yesorno) {
		this.yesorno = yesorno;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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
	public String getHeadpic() {
		return headpic;
	}
	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCalled() {
		return called;
	}
	public void setCalled(String called) {
		this.called = called;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMailbox() {
		return mailbox;
	}
	public void setMailbox(String mailbox) {
		this.mailbox = mailbox;
	}
	
	
	
}