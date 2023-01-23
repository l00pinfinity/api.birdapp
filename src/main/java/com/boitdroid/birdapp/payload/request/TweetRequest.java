package com.boitdroid.birdapp.payload.request;


import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Objects;

public class TweetRequest {

    @NotBlank
    private String body;

    private List<String> tags;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TweetRequest that = (TweetRequest) o;
        return Objects.equals(body, that.body) && Objects.equals(tags, that.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body, tags);
    }
}
