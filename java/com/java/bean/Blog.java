package com.java.bean;

import java.sql.Blob;

public class Blog {
	int Blog_id;
	Blob Blog_image;
	String description;
	String time;
	int reg_id;
	String username;
	Blob image;
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBlog_id() {
		return Blog_id;
	}
	public void setBlog_id(int blog_id) {
		Blog_id = blog_id;
	}
	public Blob getBlog_image() {
		return Blog_image;
	}
	public void setBlog_image(Blob Blog_image) {
		this.Blog_image = Blog_image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getReg_id() {
		return reg_id;
	}
	public void setReg_id(int reg_id) {
		this.reg_id = reg_id;
	}
	
	
	
}
