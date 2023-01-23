package com.boitdroid.birdapp.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class SignInRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;

    public String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SignInRequest that = (SignInRequest) o;
        return Objects.equals(usernameOrEmail, that.usernameOrEmail) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usernameOrEmail, password);
    }
}
