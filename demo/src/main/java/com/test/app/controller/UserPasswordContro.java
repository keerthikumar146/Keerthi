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

import com.test.app.domain.UserDetails;
import com.test.app.domain.UserPassword;
import com.test.app.service.UserPasswordServ;

@RestController
@RequestMapping("/password")
public class UserPasswordContro {
	
	@Autowired
	UserPasswordServ userpasswordserv;
	
	@PostMapping("/inserpassword")
	public UserPassword createUser(@RequestBody UserPassword user) {
		return userpasswordserv.createUser(user);
	}
	
	
	@GetMapping("/getalluserpasswords")
	public List<UserPassword> getAll(){
		return userpasswordserv.getAllUsers();
	}
	
	
	@GetMapping("/getuserpassword/{id}")
	public UserPassword getById(@PathVariable int id) {
		return userpasswordserv.getByUserId(id);
	}
	
	@PutMapping("/updateuserpassword")
	public UserPassword updateDetails(@RequestBody UserPassword user) {
		return userpasswordserv.updateUser(user);
	}
	
	@DeleteMapping("/deleteuserpassword/{id}")
	public String deleteDeatils(@PathVariable int id) {
		return userpasswordserv.deleteUserById(id);
	}
		
}
