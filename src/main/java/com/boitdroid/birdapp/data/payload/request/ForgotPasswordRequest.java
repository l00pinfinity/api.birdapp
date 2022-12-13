package com.boitdroid.birdapp.data.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ForgotPasswordRequest {

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Reset token cannot be null")
    @NotEmpty(message = "Reset token cannot be empty")
    private String resetToken;
}
