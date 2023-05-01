package com.lti.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserModel {
	
	private String name;
	
	@NotNull(message = "Please enter email")
	@Email(message = "Please enter valid email")
	private String email;
	
	@NotNull(message = "Please enter password")
	@Size(min=3,message = "Password must be at least 3 characters")
	private String password;
	
	private Long age=0L;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}
	
	

}
