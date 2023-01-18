package com.boitdroid.birdapp.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentRequest {

    @NotBlank
    private String body;
}
