package com.boitdroid.birdapp.payload.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ForgotPasswordRequest {

    @NotNull(message = "Password cannot be null")
    @NotEmpty(message = "Password cannot be empty")
    private String password;

    @NotNull(message = "Reset token cannot be null")
    @NotEmpty(message = "Reset token cannot be empty")
    private String resetToken;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}
