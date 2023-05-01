package com.lti.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lti.dao.UserRepository;
import com.lti.exceptions.ItemAlreadyExistsException;
import com.lti.exceptions.ResourceNotFoundException;
import com.lti.model.User;
import com.lti.model.UserModel;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private PasswordEncoder bCryptEncoder;
	
	@Autowired
	UserRepository uRepo;

	@Override
	public User saveUser(UserModel umodel) {
		
		if(uRepo.existsByEmail(umodel.getEmail())) {
			throw new ItemAlreadyExistsException("User is already registered with the email "+ umodel.getEmail());
		}
		
		User user = new User();
		BeanUtils.copyProperties(umodel, user);
		user.setPassword(bCryptEncoder.encode(user.getPassword()));
		
		User u = uRepo.save(user);
		return u;
	}

	@Override
	public User findUserById() {
		Long userId = getLoggedInUser().getId();
		Optional<User> opt = uRepo.findById(userId);
		if (opt.isPresent()) {
			return opt.get();
		}
		else
			throw new ResourceNotFoundException("The user cannot be found for the id "+userId);
	}

	@Override
	public User updateUser(User user) throws ResourceNotFoundException {
		
		User existinguser = findUserById();
		
		existinguser.setName(user.getName()!=null?user.getName():existinguser.getName());
		existinguser.setEmail(user.getEmail()!=null?user.getEmail():existinguser.getEmail());
		existinguser.setPassword(user.getPassword()!=null?bCryptEncoder.encode(user.getPassword()):existinguser.getPassword());
		existinguser.setAge(user.getAge()!=null?user.getAge():existinguser.getAge());
		
		return uRepo.save(existinguser);
	}

	@Override
	public void deleteUser() {
		
		User user = findUserById();
		
		uRepo.delete(user);
		
	}

	@Override
	public User getLoggedInUser() {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String email = authentication.getName();
		
		return uRepo.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("User not found for the email "+ email));
		
	}

}
