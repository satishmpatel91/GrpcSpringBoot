package com.grpcflix.movieservice.repository;


import com.grpcflix.movieservice.entiry.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {

    List<Movie> getMovieByGenreOrderByYears(String genre);
}
