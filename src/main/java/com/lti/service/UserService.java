package com.lti.service;

import com.lti.model.User;
import com.lti.model.UserModel;

public interface UserService {
	
	User saveUser(UserModel usermodel);
	
	User findUserById();
	
	User updateUser(User user);
	
	void deleteUser();
	
	User getLoggedInUser();
	
}
