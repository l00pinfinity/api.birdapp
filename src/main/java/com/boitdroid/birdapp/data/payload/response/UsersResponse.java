package com.boitdroid.birdapp.data.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsersResponse {

    private Boolean success;
    private String message;
    private Object data;
}
