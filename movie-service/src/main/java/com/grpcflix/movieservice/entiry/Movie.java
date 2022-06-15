package com.grpcflix.movieservice.entiry;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@ToString
public class Movie {
    @Id
    private int id;
    private String title;
    private int years;
    private double rating;
    private String genre;
}
