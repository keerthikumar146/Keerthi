package com.test.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.app.domain.UserAltrMobile;
import com.test.app.domain.UserDetails;
import com.test.app.repo.UserAltrMobileRepo;
@Service
public class UserAltrMobileServ {
	
	@Autowired
	UserAltrMobileRepo useraltrmobilerepo;
	
	public UserAltrMobile createUser(UserAltrMobile user) {
		return	useraltrmobilerepo.save(user);
	}

	/*public List<UserAltrMobile> createUsers(List<UserAltrMobile> users) {
		return useraltrmobilerepo.saveAll(users);
	}*/

	public List<UserAltrMobile> getAllUsers() {
		List<UserAltrMobile> userData = useraltrmobilerepo.findAll();
		return userData;
	}
	
	public UserAltrMobile getByUserId(int userId) {
		return useraltrmobilerepo.findById(userId).orElse(null);
	}

	public UserAltrMobile updateUser(UserAltrMobile user) {
		UserAltrMobile userd=null;
		Optional<UserAltrMobile> userdata = useraltrmobilerepo.findById(user.getSerialId());
		if (userdata.isPresent()) {
			userd = userdata.get();
			userd.setSerialId(user.getSerialId());
			userd.setMobileNum(userd.getMobileNum());
			userd.setType(userd.getType());
			userd=useraltrmobilerepo.save(userd);
			return userd;
		} else {
			return new UserAltrMobile();
		}
	}
	
	public String deleteUserById(int serialId) {
		useraltrmobilerepo.deleteById(serialId);
		return "UserDeleted";
}
}
