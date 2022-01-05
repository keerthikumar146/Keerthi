package com.test.app.domain;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name="USER_DETAILS")

public class UserDetails {
	
	
	@Id
	@Column(name="USER_ID")
	private int userId;
	
	@Column(name="USER_NAME", nullable = false)
	private String userName;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "userdetails")
	//@JoinColumn(name="USER_DETAILS_USER_ID",referencedColumnName = "user_Id")
	private List<UserAltrMobile> useraltrmobile;
	
	@OneToOne(cascade = CascadeType.ALL,mappedBy = "user")
	//@JoinColumn(name="USER_DETAILS_USER_ID",referencedColumnName = "user_Id")
	private UserPassword userpassword;
	
	
	
	public List<UserAltrMobile> getUseraltrmobile() {
		return useraltrmobile;
	}
	public void setUseraltrmobile(List<UserAltrMobile> useraltrmobile) {
		this.useraltrmobile = useraltrmobile;
	}
	public UserPassword getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(UserPassword userpassword) {
		this.userpassword = userpassword;
	}
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
	
}