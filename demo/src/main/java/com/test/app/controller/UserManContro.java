package com.test.app.controller;

import java.util.List;

import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.support.Repositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bean.UserDetailsBean;
import com.test.app.domain.LocationMaster;
import com.test.app.domain.UserDetails;
import com.test.app.repo.UserDetailsRepo;
import com.test.app.service.UserServices;

@RequestMapping("/User")
@RestController
public class UserManContro {
	
	@Autowired
	UserServices userservices;
	
	@PostMapping("/insertuser")
	public String createUser(@RequestBody UserDetailsBean user) {
		return userservices.createUser(user);
	}
	
	
	@GetMapping("/getalluserDetails")
	public List<UserDetailsBean> getAll(){
		return userservices.getAllUsers();
	}
	
	
	@GetMapping("/getuserDeatils/{userId}")
	public UserDetailsBean getById(@PathVariable int userId) {
		return userservices.getByUserId(userId);
	}
	
	@PutMapping("/updateuserDetails")
	public String updateDetails(@RequestBody UserDetailsBean user) {
		return userservices.updateUser(user);
	}
	
	@DeleteMapping("/deleteuserDetails/{userId}")
	public String deleteDeatils(@PathVariable int userId) {
		return userservices.deleteUserById(userId);
	}
		
}
