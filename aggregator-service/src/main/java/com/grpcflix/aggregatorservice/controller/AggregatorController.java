package com.grpcflix.aggregatorservice.controller;

import com.grpcflix.aggregatorservice.dto.RecommendedMovie;
import com.grpcflix.aggregatorservice.dto.UserGenre;
import com.grpcflix.aggregatorservice.service.UserMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AggregatorController {
    @Autowired
    UserMovieService userMovieService;

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        System.out.println("Login ID :- "+loginId);
        return userMovieService.getUserMovieSuggestions(loginId);
    }

    @PutMapping("/user")
    public void setUserGenre(@RequestBody UserGenre userGenre) {
        userMovieService.setUserGenre(userGenre);
    }
}
