package com.bean;

import java.util.*;
public class UserDetailsBean {
	
	
	private int userId;
	private String userName;
	private String firstName;
	private String lastName;
	private String emailId;
	private UserPasswordBean userpassword;
	private List<UserAltrMobileBean> useraltrmobile;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public UserPasswordBean getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(UserPasswordBean userpassword) {
		this.userpassword = userpassword;
	}
	public List<UserAltrMobileBean> getUseraltrmobile() {
		return useraltrmobile;
	}
	public void setUseraltrmobile(List<UserAltrMobileBean> useraltrmobile) {
		this.useraltrmobile = useraltrmobile;
	}
	
	
}
