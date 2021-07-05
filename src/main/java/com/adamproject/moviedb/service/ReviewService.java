package com.adamproject.moviedb.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.entity.Review;

@Service
public interface ReviewService {
	Review findById(Long id);
	Review saveReview(Review review);
	List<Review> lastThreeReviews(Movie movie);
}
