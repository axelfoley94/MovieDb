package com.adamproject.moviedb.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	private String roles;
	
	public List<String> getRoleList(){
		if(this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}
		return new ArrayList<>();
	}
	
	@OneToMany(
	        mappedBy = "user",
	        cascade = CascadeType.ALL)
	private Set<Review> reviews;

	@ManyToMany
	@JoinTable(
	  name = "user_movie_watchlist", 
	  joinColumns = @JoinColumn(name = "user_id"), 
	  inverseJoinColumns = @JoinColumn(name = "movie_id"))
	private List<Movie> watchList;
	
	
	public List<Movie> getWatchList() {
		return watchList;
	}

	public void setWatchList(List<Movie> watchList) {
		this.watchList = watchList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Review> getReviews() {
		return reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public User(String username, String password, String roles, Set<Review> reviews) {
		this.username = username;
		this.password = password;
		this.roles = roles;
		this.reviews = reviews;
	}	
	
	public User() {
		
	}

	
}
