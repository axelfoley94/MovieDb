package com.adamproject.moviedb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.entity.User;
import com.adamproject.moviedb.service.MovieService;
import com.adamproject.moviedb.service.MyUserDetailsService;


@Controller
public class HomeController {

	PasswordEncoder passwordEnconder;
	MovieService movieService;
	MyUserDetailsService userService;

	@Autowired
	public HomeController(PasswordEncoder passwordEnconder, MovieService movieService,
			MyUserDetailsService userService) {
		super();
		this.passwordEnconder = passwordEnconder;
		this.movieService = movieService;
		this.userService = userService;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/register")
	public String regGet(Model model) {
		
		User newUser = new User();	
		
		model.addAttribute("newUser",newUser);
		String password2 = "";
		model.addAttribute("password2",password2);
		
		return "register";
	}
	
	
	@PostMapping("/register")
	public String regPost(@ModelAttribute("newUser") User regUser,@ModelAttribute("password2") String password2,RedirectAttributes redirectAttributes) {
		
		if(userService.findByUserName(regUser.getUsername()) != null) {
			redirectAttributes.addFlashAttribute("usernameError","Username is already taken!");
			return "redirect:/register";
		}

		if(!regUser.getPassword().equals(password2)) {
			redirectAttributes.addFlashAttribute("passwordError","Passwords do not match!");
			return "redirect:/register";
		}
		
		regUser.setRoles("USER");
		regUser.setPassword(passwordEnconder.encode(password2));
		userService.save(regUser);
		
		
		return "login";
	}
	
	@GetMapping("/")
	public String homePost(Model model,@AuthenticationPrincipal UserDetails myUser) {
		//comment ssh test 4es teszt
		
		model.addAttribute("movies",movieService.findNewestMovies());
		
		return "index";
	}
	
}
