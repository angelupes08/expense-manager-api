package com.lti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lti.model.AuthModel;
import com.lti.model.JwtResponse;
import com.lti.model.User;
import com.lti.model.UserModel;
import com.lti.security.CustomUserDetailsService;
import com.lti.service.UserService;
import com.lti.util.JwtTokenUtil;

import jakarta.validation.Valid;

@RestController
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	UserService uService;
	
	@Autowired
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> loginUser(@RequestBody AuthModel authmodel) throws Exception {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authmodel.getEmail(),authmodel.getPassword()));
		
		authenticate(authmodel.getEmail(),authmodel.getPassword());
		
		//we need to generate the jwt token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authmodel.getEmail());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		return new ResponseEntity<JwtResponse>(new JwtResponse(token),HttpStatus.OK);

		}
	private void authenticate(String email, String password) throws Exception {
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
			
		}catch (DisabledException e) {
			throw new Exception("User disabled");
		}catch(BadCredentialsException e) {
			throw new Exception("Bad credentials");
		}
	}
	
	@ResponseStatus(value=HttpStatus.CREATED)
	@PostMapping("/register")
	public User createUser(@Valid @RequestBody UserModel umodel) {
		return uService.saveUser(umodel);
		
	}
}
