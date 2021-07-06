package com.adamproject.moviedb.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.adamproject.moviedb.entity.User;
import com.adamproject.moviedb.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {

	@Mock
	UserRepository userRepository;
	
	MyUserDetailsService userService;
	
	@BeforeEach
	void setUp() {
		userService = new MyUserDetailsService(userRepository);
	}
	
	@Test
	void testLoadUserByUsername() {
		String username = "adam";
		User user = new User("adam","adamka","ADMIN",null);
		
		Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

		assertThat(userService.findByUserName(username)).isEqualTo(user);
		
		
	}
	
	@Test
	void testLoadUserByUsernameFail() {
		
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenThrow(UsernameNotFoundException.class);

		assertThatThrownBy(() -> {
			userService.loadUserByUsername(Mockito.anyString());
		}).isInstanceOf(UsernameNotFoundException.class);
		
		
	}

	@Test
	void testSave() {
		User user = new User("testus","testpw","ADMIN",null);
		userService.save(user);
		
		verify(userRepository).save(user);
	}

}
