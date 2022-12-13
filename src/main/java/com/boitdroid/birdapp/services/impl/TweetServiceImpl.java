package com.boitdroid.birdapp.services.impl;

import com.boitdroid.birdapp.data.models.Tweets;
import com.boitdroid.birdapp.data.payload.request.TweetRequest;
import com.boitdroid.birdapp.repository.TweetsRepository;
import com.boitdroid.birdapp.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetsRepository tweetsRepository;

    @Override
    public Object postTweet(TweetRequest tweetRequest) {
        Tweets tweet = new Tweets();
        tweet.setTweet(tweetRequest.getTweet());
        return tweetsRepository.save(tweet);
    }

    @Override
    public Object updateTweet(TweetRequest tweetRequest, long id) {
        return null;
    }

    @Override
    public Optional<Tweets> findTweetById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Tweets> allTweets() {
        return null;
    }

    @Override
    public Optional<Tweets> findTweetById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteTweet(Long id) {

    }
}
