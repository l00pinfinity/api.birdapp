package com.boitdroid.birdapp.utils;

import com.boitdroid.birdapp.exception.AppException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public static String generateToken(){
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";

        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();

        for(int i = 0; i < 6; i++) {
            int index = random.nextInt(alphaNumeric.length());
            char randomChar = alphaNumeric.charAt(index);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static List<String> hashtagsInTweet(String tweet) {
        List<String> hashtags = new ArrayList<>();
        String[] words = tweet.split(" ");
        for (String word : words) {
            if (word.startsWith("#")) {
                String hashtag = word.substring(1);
                hashtags.add(hashtag);
            }
        }
        return hashtags;
    }

}
