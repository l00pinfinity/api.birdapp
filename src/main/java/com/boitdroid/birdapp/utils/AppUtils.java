package com.boitdroid.birdapp.utils;

import com.boitdroid.birdapp.exception.AppException;

public class AppUtils {

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0){
            throw new AppException("Page number cannot be less than zero");
        }
        if (size < 0){
            throw new AppException("Size number cannot be less than zero");
        }
        if (size > 10){
            throw new AppException("Page size must not be greater than 10");
        }
    }
}
