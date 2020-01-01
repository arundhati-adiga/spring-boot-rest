package com.apiapp.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService { 

	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	public UserDetails loadUserByUsername(String username) {
		if("admin".equals(username)) {
			
			//return (new UserDetails("admin",new BCryptPasswordEncoder().encode("password"),new ArrayList<>()));
			return new org.springframework.security.core.userdetails.User("admin", bcryptEncoder.encode("password"),
					new ArrayList<>());
			
		}
		throw new BadCredentialsException("User Not found");
	}
}
