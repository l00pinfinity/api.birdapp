package com.boitdroid.birdapp.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.Objects;


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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getSuite() {
        return suite;
    }

    public void setSuite(String suite) {
        this.suite = suite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoRequest that = (InfoRequest) o;
        return Objects.equals(street, that.street) && Objects.equals(suite, that.suite) && Objects.equals(city, that.city) && Objects.equals(country, that.country) && Objects.equals(zipcode, that.zipcode) && Objects.equals(lat, that.lat) && Objects.equals(lng, that.lng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, suite, city, country, zipcode, lat, lng);
    }
}
