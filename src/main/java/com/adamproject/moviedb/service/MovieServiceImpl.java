package com.adamproject.moviedb.service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.repository.MovieRepository;


@Service
public class MovieServiceImpl implements MovieService {
	
	MovieRepository movieRepository;
	
	@Autowired
	public void setMovieRepository(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}
	@Override
	public List<Movie> findAll() {

		return movieRepository.findAll();
	}
	@Override
	public Movie findOne(Long id) {
		
		return movieRepository.findById(id).orElse(null);
	}
	@Override
	public Movie findByMovieTitle(String title) {
		
		return movieRepository.findByTitle(title).orElse(null);
	}
	@Override
	public Movie addMovie(Movie movie) {
		
		return movieRepository.save(movie);
	}
	
	@Override
	public Set<Movie> findByReleseDate(int year,int count) {
		
		List<Movie> fileredMovie = movieRepository.findAll().stream().filter(m -> m.getReleseDate().getYear() == year).collect(Collectors.toList());
		Collections.shuffle(fileredMovie);
		return fileredMovie.stream().limit(count).collect(Collectors.toSet());
	}

	@Override
	public Set<Movie> findxRandomMovie(int count) {
				
		Random ran = new Random();
		int randomYear = 1980 + ran.nextInt(2020 - 1980 + 1);
		return findByReleseDate(randomYear,count);
	}
	

	@Override
	public Page<Movie> findPaginated(int pageNo, int pageSize) {
		 Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
		 return movieRepository.findAll(pageable);
	}

	@Override
	public Page<Movie> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		 Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
		 Sort.by(sortField).descending();
		 
		 Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

		 return movieRepository.findAll(pageable);
		 
	}
	@Override
	public Set<Movie> findNewestMovies() {
		return movieRepository.findThreeNewest();
	}

}
