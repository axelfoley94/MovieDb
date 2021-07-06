package com.adamproject.moviedb.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adamproject.moviedb.entity.MyUserDetails;
import com.adamproject.moviedb.entity.User;
import com.adamproject.moviedb.repository.UserRepository;
@Service
public class MyUserDetailsService implements UserDetailsService{
	
	private UserRepository userRepository;
	
	public MyUserDetailsService(UserRepository userRepository) { 
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		MyUserDetails myUserDetails = new MyUserDetails(user);
		
		return myUserDetails;
	}

	public User findByUserName(String username){
		return userRepository.findByUsername(username).orElse(null);
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}

}
