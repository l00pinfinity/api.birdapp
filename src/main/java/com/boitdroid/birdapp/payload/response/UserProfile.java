package com.boitdroid.birdapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {

    private Long id;
    private String name;
    private String username;
    private Instant joinedAt;
    private Long tweetCount;

}
