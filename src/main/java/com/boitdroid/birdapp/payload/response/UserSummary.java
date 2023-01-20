package com.boitdroid.birdapp.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummary {

    private Long id;
    private String name;
    private String username;
    private String email;
    private Boolean isEnabled;
}
