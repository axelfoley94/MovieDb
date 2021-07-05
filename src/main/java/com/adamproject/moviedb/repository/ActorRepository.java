package com.adamproject.moviedb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.adamproject.moviedb.entity.Actor;


public interface ActorRepository extends JpaRepository<Actor, Long> {
	Optional<Actor> findById(Long id);
	List<Actor> findAll();
	Optional<Actor> findByName(String name);
	Page<Actor> findByNameContainingAllIgnoreCase(String name,Pageable pageable);
}
