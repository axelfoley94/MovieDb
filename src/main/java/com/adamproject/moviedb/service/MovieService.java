package com.adamproject.moviedb.service;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.adamproject.moviedb.entity.Movie;

public interface MovieService {
	
	List<Movie> findAll();
	Movie findOne(Long id);
	Movie findByMovieTitle(String title);
	Movie addMovie(Movie movie);
	Set<Movie> findxRandomMovie(int count);
	Set<Movie> findByReleseDate(int year,int count);
	
	Set<Movie> findNewestMovies();
	
	Page<Movie> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	Page<Movie> findPaginated(int pageNo, int pageSize);
	

}
