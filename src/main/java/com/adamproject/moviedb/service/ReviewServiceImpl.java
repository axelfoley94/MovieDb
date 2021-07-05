package com.adamproject.moviedb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.entity.Review;
import com.adamproject.moviedb.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	ReviewRepository reviewRepository;

	@Override
	public Review findById(Long id) {
		return reviewRepository.findById(id).orElse(null);

	}

	@Override
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}

	public List<Review> lastThreeReviews(Movie movie) {
		return reviewRepository.findTop3ByMovieOrderByIdDesc(movie);
	}
}
