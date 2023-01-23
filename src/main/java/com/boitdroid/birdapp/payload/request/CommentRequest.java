package com.boitdroid.birdapp.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class CommentRequest {

    @NotBlank
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CommentRequest that = (CommentRequest) o;
        return Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }
}
