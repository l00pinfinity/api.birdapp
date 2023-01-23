package com.boitdroid.birdapp.payload.response;

import java.util.Objects;

public class UserSummary {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Boolean isEnabled;

    public UserSummary(Long id, String name, String username, String email, Boolean isEnabled) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.isEnabled = isEnabled;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserSummary that = (UserSummary) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(username, that.username) && Objects.equals(email, that.email) && Objects.equals(isEnabled, that.isEnabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, isEnabled);
    }
}
