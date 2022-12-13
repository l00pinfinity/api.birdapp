package com.boitdroid.birdapp.data.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {

    @NotNull(message = "Username/Email cannot be null")
    @NotEmpty(message = "Username/Email cannot be empty")
    private String usernameOrEmail;

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
}
