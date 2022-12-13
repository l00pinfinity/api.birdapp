package com.boitdroid.birdapp.services;

import com.boitdroid.birdapp.data.models.Tweets;
import com.boitdroid.birdapp.data.payload.request.TweetRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TweetService {
    Object postTweet(TweetRequest tweetRequest);
    Object updateTweet(TweetRequest tweetRequest, long id);
    Optional<Tweets> findTweetById(long id);
    List<Tweets> allTweets();
    Optional<Tweets> findTweetById(Long id);
    void deleteTweet(Long id);
}
