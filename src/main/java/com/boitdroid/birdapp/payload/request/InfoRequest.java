package com.boitdroid.birdapp.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class InfoRequest {

    @NotBlank
    private String street;

    @NotBlank
    private String suite;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String zipcode;

    private String lat;

    private String lng;
}
