package com.lti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.User;
import com.lti.model.UserModel;
import com.lti.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	
	@Autowired
	UserService uService;
	
	
	@GetMapping("/profile")
	public User findUserById() {
		return uService.findUserById();
		
	}
	
	@PutMapping("/profile")
	public User updateUser (@RequestBody User user) {
		return uService.updateUser(user);
	}
	
	@DeleteMapping("/deactivate")
	public String deleteUser () {
		uService.deleteUser();
		return "The user has been deleted";
	}
}
