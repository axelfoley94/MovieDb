package com.adamproject.moviedb.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.adamproject.moviedb.entity.Movie;
import com.adamproject.moviedb.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
	Optional<Review> findById(Long id);
	
	//@Query("SELECT o from OltasEgyseg o WHERE (:datum is null or o.datum = :datum) AND (:vakcina is null or o.oltasPraxis.vakcina.nev = :vakcina)")
	//List<OltasEgyseg> findByDatumVakcina(@Param("datum") LocalDate datum,@Param("vakcina") String vakcina);
	
	List<Review>findTop3ByMovieOrderByIdDesc(Movie movie);
}
