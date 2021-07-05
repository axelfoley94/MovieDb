package com.adamproject.moviedb.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.adamproject.moviedb.entity.User;
import com.adamproject.moviedb.service.MovieService;
import com.adamproject.moviedb.service.MyUserDetailsService;

@Controller
public class UserController {

	MyUserDetailsService userService;
	MovieService movieService;
	
	public UserController(MyUserDetailsService userService, MovieService movieService) {
		super();
		this.userService = userService;
		this.movieService = movieService;
	}

	@GetMapping("/user")
	public String userDetails(@AuthenticationPrincipal UserDetails userDetails,Model model) {
		
		User currentUser = userService.findByUserName(userDetails.getUsername());
		model.addAttribute("currentUser",currentUser);
		
		return "userDetails";
	}
	
	@PostMapping("addWatchList")
	public String addMovieMyWatchList(@RequestParam("id") Long movieId,@AuthenticationPrincipal UserDetails userDetails) {
		
		User currentUser = userService.findByUserName(userDetails.getUsername());
		System.out.println(currentUser.getId());
		currentUser.getWatchList().add(movieService.findOne(movieId));
		userService.save(currentUser);
		
		return "redirect:/movie?id="+movieId;
	}
	
	@PostMapping("removeWatchList")
	public String removeMovieMyWatchList(@RequestParam("id") Long movieId,@AuthenticationPrincipal UserDetails userDetails) {
		
		User currentUser = userService.findByUserName(userDetails.getUsername());
		currentUser.getWatchList().remove(movieService.findOne(movieId));
		userService.save(currentUser);
		
		return "redirect:/movie?id="+movieId;
	}
	
}
