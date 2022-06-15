package com.grpcflix.movieservice.service;

import com.grpcflix.common.Genre;
import com.grpcflix.movieservice.MovieDto;
import com.grpcflix.movieservice.MovieSearchRequest;
import com.grpcflix.movieservice.MovieSearchResponse;
import com.grpcflix.movieservice.MovieServiceGrpc;
import com.grpcflix.movieservice.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class MovieService extends MovieServiceGrpc.MovieServiceImplBase {

    @Autowired
    MovieRepository movieRepository;

    @Override
    public void getMovies(MovieSearchRequest request, StreamObserver<MovieSearchResponse> responseObserver) {

        List<MovieDto> movieDtos = movieRepository.getMovieByGenreOrderByYears(request.getGenre().toString())
                .stream().map(movie -> MovieDto.newBuilder()
                        .setTitle(movie.getTitle())
                        .setYear(movie.getYears())
                        .setRating(movie.getRating())
                        .build())
                .collect(Collectors.toList());
        System.out.println(movieDtos);
        MovieSearchResponse movieSearchResponse = MovieSearchResponse.newBuilder().addAllMovie(movieDtos).build();
        responseObserver.onNext(movieSearchResponse);
        responseObserver.onCompleted();

        //super.getMovies(request, responseObserver);
    }
}
