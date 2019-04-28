package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="schools")
public class School {
	@Id
	private String school;
	@Column
	private String address;
	@Column
	private String type;
	@Column
	private String nature;
	@Column
	private String or985;
	@Column
	private String or211;
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getOr985() {
		return or985;
	}
	public void setOr985(String or985) {
		this.or985 = or985;
	}
	public String getOr211() {
		return or211;
	}
	public void setOr211(String or211) {
		this.or211 = or211;
	}

}