package com.test.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.app.domain.UserAltrMobile;
import com.test.app.service.UserAltrMobileServ;

@RestController
@RequestMapping("/usermobile")
public class UserAltrMobileContr {
	
	@Autowired
	UserAltrMobileServ useraltrmobileserv;

	@PostMapping("/insertuser")
	public UserAltrMobile createUser(@RequestBody UserAltrMobile user) {
		return useraltrmobileserv.createUser(user);
	}
	
	/*@PostMapping("/insertusers")
	public List<UserAltrMobile> createUsers(@RequestBody List<UserAltrMobile> user)  {
		return useraltrmobileserv.createUsers(user);
	}*/
	
	@GetMapping("/getalluserDetails")
	public List<UserAltrMobile> getAll(){
		return useraltrmobileserv.getAllUsers();
	}
	
	
	@GetMapping("/getuserDeatils/{userId}")
	public UserAltrMobile getById(@PathVariable int userId) {
		return useraltrmobileserv.getByUserId(userId);
	}
	
	@PutMapping("/updateuserDetails")
	public UserAltrMobile updateDetails(@RequestBody UserAltrMobile user) {
		return useraltrmobileserv.updateUser(user);
	}
	
	@DeleteMapping("/deleteuserDetails/{userId}")
	public String deleteDeatils(@PathVariable int userId) {
		return useraltrmobileserv.deleteUserById(userId);
	}
		
}
