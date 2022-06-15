package com.grpcflix.userservice.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@ToString
public class Users {

    @Id
    private String login;
    private String name;
    private String genre;
}
