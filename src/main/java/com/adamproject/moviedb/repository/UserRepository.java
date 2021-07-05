package com.adamproject.moviedb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adamproject.moviedb.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
