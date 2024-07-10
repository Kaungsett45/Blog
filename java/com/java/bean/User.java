package com.java.bean;

import java.sql.Blob;

public class User {
	int reg_id;
	String username;
	Blob image;
	String email;
	String password;
	String chgpassword;
	String gender;
	String passport_id;
	int Blog_id;
	

	public int getBlog_id() {
		return Blog_id;
	}
	public void setBlog_id(int blog_id) {
		Blog_id = blog_id;
	}
	public int getReg_id() {
		return reg_id;
	}
	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getChgpassword() {
		return chgpassword;
	}
	public void setChgpassword(String chgpassword) {
		this.chgpassword = chgpassword;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassport_id() {
		return passport_id;
	}
	public void setPassport_id(String passport_id) {
		this.passport_id = passport_id;
	}
	
	
}
