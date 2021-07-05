package com.adamproject.moviedb.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.adamproject.moviedb.entity.Movie;


public interface MovieRepository extends CrudRepository<Movie, Long>{

	Optional<Movie> findById(Long id);
	List<Movie> findAll();
	Optional<Movie> findByTitle(String title);
	Set<Movie> findByReleseDate(Integer year);
	
	@Query(value="select * from movie",nativeQuery = true)
	Page<Movie> findAll(Pageable pageable);
	
	@Query(value="select * from moviedb.movie order by id desc",nativeQuery = true)
	Set<Movie> findThreeNewest();
}

