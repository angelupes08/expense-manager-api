package com.lti.security;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lti.dao.UserRepository;
import com.lti.model.User;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		Optional<User> opt= userRepository.findByEmail(email);
		if (opt.isPresent()==false) {
			throw new UsernameNotFoundException("User not found for the email " + email);
			
		}
		User existingUser = opt.get();
		return new org.springframework.security.core.userdetails.User(existingUser.getEmail(),existingUser.getPassword(), new ArrayList<>());
	}

}
