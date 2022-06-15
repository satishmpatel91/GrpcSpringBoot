package com.grpcflix.aggregatorservice.service;

import com.grpcflix.aggregatorservice.dto.RecommendedMovie;
import com.grpcflix.aggregatorservice.dto.UserGenre;
import com.grpcflix.common.Genre;
import com.grpcflix.movieservice.MovieSearchRequest;
import com.grpcflix.movieservice.MovieSearchResponse;
import com.grpcflix.movieservice.MovieServiceGrpc;
import com.grpcflix.userservice.UserGenreUpdate;
import com.grpcflix.userservice.UserResponse;
import com.grpcflix.userservice.UserSearchRequest;
import com.grpcflix.userservice.UserServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceGrpc.UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceGrpc.MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestions(String loginId){
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId).build();
        UserResponse userResponse = this.userStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder().setGenre(userResponse.getGenre()).build();
        MovieSearchResponse movieSearchResponse = this.movieStub.getMovies(movieSearchRequest);
        return movieSearchResponse.getMovieList()
                .stream()
                .map(movieDto -> new RecommendedMovie(movieDto.getTitle(), movieDto.getYear(), movieDto.getRating()))
                .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre){
        UserGenreUpdate userGenreUpdateRequest = UserGenreUpdate.newBuilder()
                .setLoginId(userGenre.getLoginId())
                .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
                .build();
        UserResponse userResponse = this.userStub.updateUserGenre(userGenreUpdateRequest);
    }
}
