package com.boitdroid.birdapp.payload.request;

import java.util.Objects;

public class UserIdentityAvailability {
    private Boolean available;

    public UserIdentityAvailability(Boolean available) {
        this.available = available;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserIdentityAvailability that = (UserIdentityAvailability) o;
        return Objects.equals(available, that.available);
    }

    @Override
    public int hashCode() {
        return Objects.hash(available);
    }
}
