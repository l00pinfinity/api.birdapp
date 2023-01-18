package com.boitdroid.birdapp.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class TweetRequest {

    @NotBlank
    private String body;

    private List<String> tags;
}
