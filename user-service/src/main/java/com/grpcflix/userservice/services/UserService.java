package com.grpcflix.userservice.services;

import com.grpcflix.common.Genre;
import com.grpcflix.userservice.UserGenreUpdate;
import com.grpcflix.userservice.UserResponse;
import com.grpcflix.userservice.UserSearchRequest;
import com.grpcflix.userservice.UserServiceGrpc;
import com.grpcflix.userservice.repository.UserRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class UserService extends UserServiceGrpc.UserServiceImplBase {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void getUserGenre(UserSearchRequest request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    builder.setName(user.getName());
                    builder.setLoginId(user.getLogin());
                    builder.setGenre(Genre.valueOf(user.getGenre()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void updateUserGenre(UserGenreUpdate request, StreamObserver<UserResponse> responseObserver) {
        UserResponse.Builder builder = UserResponse.newBuilder();
        userRepository.findById(request.getLoginId())
                .ifPresent(user -> {
                    user.setGenre(request.getGenre().toString());
                    builder.setName(user.getName());
                    builder.setLoginId(user.getLogin());
                    builder.setGenre(Genre.valueOf(user.getGenre()));
                });
        responseObserver.onNext(builder.build());
        responseObserver.onCompleted();
    }
}
