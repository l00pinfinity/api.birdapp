package com.boitdroid.birdapp.payload.response;

import java.time.Instant;
import java.util.Objects;

public class UserProfile {

    private Long id;
    private String name;
    private String username;
    private Instant joinedAt;
    private Long tweetCount;

    public UserProfile(Long id, String name, String username, Instant joinedAt, Long tweetCount) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.joinedAt = joinedAt;
        this.tweetCount = tweetCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getTweetCount() {
        return tweetCount;
    }

    public void setTweetCount(Long tweetCount) {
        this.tweetCount = tweetCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(username, that.username) && Objects.equals(joinedAt, that.joinedAt) && Objects.equals(tweetCount, that.tweetCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, joinedAt, tweetCount);
    }
}
