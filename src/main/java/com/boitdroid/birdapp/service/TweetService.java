package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.post.Tweet;
import com.boitdroid.birdapp.payload.request.TweetRequest;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import org.springframework.stereotype.Service;

@Service
public interface TweetService {

    PagedResponse<Tweet> getAllPosts(int page, int size);
    PagedResponse<Tweet> getPostsByCreatedBy(String username, int page, int size);
    PagedResponse<Tweet> getPostsByTag(Long id, int page, int size);
    ApiResponse updatePost(Long id, TweetRequest tweetRequest, UserPrincipal userPrincipal);
    ApiResponse deletePost(Long id,UserPrincipal userPrincipal);
    ApiResponse addPost(TweetRequest tweetRequest, UserPrincipal userPrincipal);
    Tweet getPostById(Long id);
}
