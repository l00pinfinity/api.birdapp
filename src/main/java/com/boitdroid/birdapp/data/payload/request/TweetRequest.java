package com.boitdroid.birdapp.data.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TweetRequest {

    @NotNull(message = "Tweet cannot be null")
    @NotEmpty(message = "Tweet cannot be empty")
    private String tweet;
}
