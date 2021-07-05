package com.adamproject.moviedb.entity;

import java.time.LocalDate;
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

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	private int runtime;
	private String description;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate releseDate;
	private String category;
	
	private String imgName;
	
	@ManyToMany(mappedBy = "watchList")
	Set<User> userWatchListed;
	
	public String getImgName() {
		return imgName;
	}


	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	@OneToMany(
	        mappedBy = "movie",
	        cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@ManyToMany
    @JoinTable(
       name="movie_actor",
		   joinColumns = @JoinColumn(name = "movie_id",referencedColumnName="id"), 
		   inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName="id"))
	private List<Actor> actors;
	
	
//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinColumn(name = "movie_id")
//	private List <Actor> actors;

	//@Transient
	public String getLogoImagePath() {
		return "brand-logos/"+this.id+"/"+this.imgName;
	}
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public LocalDate getReleseDate() {
		return releseDate;
	}

	public void setReleseDate(LocalDate releseDate) {
		this.releseDate = releseDate;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getRuntime() {
		return runtime;
	}

	public void setRuntime(int runtime) {
		this.runtime = runtime;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public void setActors(List<Actor> actors) {
		this.actors = actors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}
	
}
