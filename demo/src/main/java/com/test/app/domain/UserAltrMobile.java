package com.test.app.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ManyToAny;

@Entity
@Table(name="USER_ALTERNATE_MOBILE_NUMBERS")
public class UserAltrMobile {
	
	@Id
	@Column(name="SERIAL_ID")
	private int serialId;
	
	@Column(name="MOBILE_NUM")
	private String mobileNum;
	
	@Column(name="TYPE")
	private String type;
	
	@ManyToOne(cascade =CascadeType.ALL )
	@JoinColumn(name="USER_DETAILS_USER_ID")
	private UserDetails userdetails;

	public int getSerialId() {
		return serialId;
	}

	public UserDetails getUserdetails() {
		return userdetails;
	}

	public void setUserdetails(UserDetails userdetails) {
		this.userdetails = userdetails;
	}

	public void setSerialId(int serialId) {
		this.serialId = serialId;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
