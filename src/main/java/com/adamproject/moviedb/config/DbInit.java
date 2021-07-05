package com.adamproject.moviedb.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.adamproject.moviedb.entity.User;
import com.adamproject.moviedb.repository.UserRepository;

@Service
public class DbInit implements CommandLineRunner{

	private UserRepository userRepository;
	private PasswordEncoder passwordEnconder;
	
	@Autowired
	public DbInit(UserRepository userRepository, PasswordEncoder passwordEnconder) {
		this.userRepository = userRepository;
		this.passwordEnconder = passwordEnconder;
	}

	@Override
	public void run(String... args) throws Exception {
		
//		userRepository.deleteAll();
//		
//		User adam = new User("adam",passwordEnconder.encode("adam"),"ADMIN",null);
//		User dan = new User("david",passwordEnconder.encode("david"),"ADMIN",null);
//		
//		List<User> users = Arrays.asList(adam,dan);
//		
//		this.userRepository.saveAll(users);
		
	}

}
