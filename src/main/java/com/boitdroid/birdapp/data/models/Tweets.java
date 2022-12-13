package com.boitdroid.birdapp.data.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tweets")
public class Tweets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Lob
    private String tweet;

    private int likes;
}
