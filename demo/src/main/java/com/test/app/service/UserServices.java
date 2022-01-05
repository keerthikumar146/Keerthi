package com.test.app.service;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bean.UserAltrMobileBean;
import com.bean.UserDetailsBean;
import com.bean.UserPasswordBean;
import com.test.app.domain.UserAltrMobile;
import com.test.app.domain.UserDetails;
import com.test.app.domain.UserPassword;
import com.test.app.repo.UserAltrMobileRepo;
import com.test.app.repo.UserDetailsRepo;


@Service
public class UserServices {

	private static final Logger log = LoggerFactory.getLogger(UserServices.class); 
	
	@Autowired
	UserDetailsRepo userdetailsrepo;
	
	/*@Autowired
	UserAltrMobileRepo useraltrmobilerepo;*/

	public String createUser(UserDetailsBean user) {
		UserDetails userdata=new UserDetails();
		//List<UserDetails> userdetailsbean=new ArrayList<UserDetails>();

		UserDetailsBean userbean=new UserDetailsBean();
		userdata.setEmailId(user.getEmailId());
		userdata.setFirstName(user.getFirstName());
		userdata.setLastName(user.getLastName());
		userdata.setUserId(user.getUserId());
		userdata.setUserName(user.getUserName());
		UserPassword userpass=new UserPassword();
		UserPasswordBean userp=user.getUserpassword();
		if(userp!=null) {
			userpass.setId(userp.getId());
			userpass.setPassword(userp.getPassword());
		}
		userpass.setUser(userdata);
		userdata.setUserpassword(userpass);
		List<UserAltrMobile> useraltrm=new ArrayList<UserAltrMobile>();
		List<UserAltrMobileBean> useraltr=user.getUseraltrmobile();
		if(useraltr!=null) {
			for(UserAltrMobileBean us:useraltr) {
				UserAltrMobile useraltrmobile= new UserAltrMobile();
				useraltrmobile.setMobileNum(us.getMobileNum());
				useraltrmobile.setSerialId(us.getSerialId());
				useraltrmobile.setType(us.getType());
				useraltrmobile.setUserdetails(userdata);
				useraltrm.add(useraltrmobile);
			}
		}
		userdata.setUseraltrmobile(useraltrm);
		userdetailsrepo.save(userdata);
		
		return "Data inserted";

	}

	public List<UserDetailsBean> getAllUsers() {
		List<UserDetails> userData = userdetailsrepo.findAll();
		List<UserDetailsBean> userdetailsbean=new ArrayList<UserDetailsBean>();
		for(UserDetails user:userData) {
			UserDetailsBean userbean=new UserDetailsBean();
			userbean.setEmailId(user.getEmailId());
			userbean.setFirstName(user.getFirstName());
			userbean.setLastName(user.getLastName());
			userbean.setUserId(user.getUserId());
			userbean.setUserName(user.getUserName());
			UserPasswordBean userpass=new UserPasswordBean();
			UserPassword userp=user.getUserpassword();
			if(userp!=null) {
				userpass.setId(userp.getId());
				userpass.setPassword(userp.getPassword());
			}
			userbean.setUserpassword(userpass);
			List<UserAltrMobileBean> useraltrbean=new ArrayList<UserAltrMobileBean>();
			List<UserAltrMobile> useraltr=user.getUseraltrmobile();
			if(useraltr!=null) {
				for(UserAltrMobile us:useraltr) {
					UserAltrMobileBean useraltrmobilebean= new UserAltrMobileBean();
					useraltrmobilebean.setMobileNum(us.getMobileNum());
					useraltrmobilebean.setSerialId(us.getSerialId());
					useraltrmobilebean.setType(us.getType());
					useraltrbean.add(useraltrmobilebean);
				}
			}
			userbean.setUseraltrmobile(useraltrbean);
			userdetailsbean.add(userbean);
		}
		return userdetailsbean;
	}
	
	public UserDetailsBean getByUserId(int userId) {
		Optional<UserDetails> userdata=userdetailsrepo.findById(userId);
		UserDetails user=new UserDetails();
		UserDetailsBean userbean=new UserDetailsBean();
		if(userdata.isPresent()) {
			user= userdata.get();
		userbean.setEmailId(user.getEmailId());
		userbean.setFirstName(user.getFirstName());
		userbean.setLastName(user.getLastName());
		userbean.setUserId(user.getUserId());
		userbean.setUserName(user.getUserName());
		UserPasswordBean userpass=new UserPasswordBean();
		UserPassword userp=user.getUserpassword();
		if(userp!=null) {
			userpass.setId(userp.getId());
			userpass.setPassword(userp.getPassword());
		}
		userbean.setUserpassword(userpass);
		List<UserAltrMobileBean> useraltrbean=new ArrayList<UserAltrMobileBean>();
		List<UserAltrMobile> useraltr=user.getUseraltrmobile();
		if(useraltr!=null) {
			for(UserAltrMobile us:useraltr) {
				UserAltrMobileBean useraltrmobilebean= new UserAltrMobileBean();
				useraltrmobilebean.setMobileNum(us.getMobileNum());
				useraltrmobilebean.setSerialId(us.getSerialId());
				useraltrmobilebean.setType(us.getType());
				useraltrbean.add(useraltrmobilebean);
			}
		}
		userbean.setUseraltrmobile(useraltrbean);
		}
		return userbean;
	}

	/*public UserDetails getByUserId(int userId) {
		return userdetailsrepo.findById(userId).orElse(null);
	}*/

	public String updateUser(UserDetailsBean user) {
		UserDetails userd=null;
		//UserDetailsBean userbean=new UserDetailsBean();
		Optional<UserDetails> userdata = userdetailsrepo.findById(user.getUserId());
		if (userdata.isPresent()) {
			userd = userdata.get();
			userd.setUserId(user.getUserId());
			userd.setUserName(user.getUserName());
			userd.setFirstName(user.getFirstName());
			userd.setLastName(user.getLastName());
			userd.setEmailId(user.getEmailId());
			UserPassword userpass=new UserPassword();
			UserPasswordBean userp=user.getUserpassword();
			if(userp!=null) {
				userpass.setId(userp.getId());
				userpass.setPassword(userp.getPassword());
			}
			userpass.setUser(userd);
			userd.setUserpassword(userpass);
			List<UserAltrMobile> useraltrm=new ArrayList<UserAltrMobile>();
			List<UserAltrMobileBean> useraltr=user.getUseraltrmobile();
			if(useraltr!=null) {
				for(UserAltrMobileBean us:useraltr) {
					UserAltrMobile useraltrmobile= new UserAltrMobile();
					useraltrmobile.setMobileNum(us.getMobileNum());
					useraltrmobile.setSerialId(us.getSerialId());
					useraltrmobile.setType(us.getType());
					useraltrmobile.setUserdetails(userd);
					useraltrm.add(useraltrmobile);
				}
			}
			userd.setUseraltrmobile(useraltrm);
			userdetailsrepo.save(userd);
			
			return "Updated";
		} else {
			 log.error("User not present in db");
			return "enter correct details";
		}
	}
	
	public String deleteUserById(int userId) {
		userdetailsrepo.deleteById(userId);
		return "UserDeleted";
}
}