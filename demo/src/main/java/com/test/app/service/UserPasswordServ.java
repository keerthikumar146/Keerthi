package com.test.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.app.domain.UserDetails;
import com.test.app.domain.UserPassword;
import com.test.app.repo.UserPasswordRepo;

@Service
public class UserPasswordServ {
	
	@Autowired
	UserPasswordRepo userpasswordrepo;
	
	public UserPassword createUser(UserPassword user) {
		return userpasswordrepo.save(user);
	}

	public List<UserPassword> getAllUsers() {
		List<UserPassword> userData = userpasswordrepo.findAll();
		return userData;
	}

	public UserPassword getByUserId(int id) {
		return userpasswordrepo.findById(id).orElse(null);
	}

	public UserPassword updateUser(UserPassword user) {
		UserPassword userd = null;
		Optional<UserPassword> userdata = userpasswordrepo.findById(user.getId());
		if (userdata.isPresent()) {
			userd = userdata.get();
			userd.setId(user.getId());
			userd.setPassword(user.getPassword());
			userd = userpasswordrepo.save(userd);
			return userd;
		} else {
			return new UserPassword();
		}
	}

	public String deleteUserById(int id) {
		userpasswordrepo.deleteById(id);
		return "UserDeleted";
	}
}
