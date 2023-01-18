package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.post.Comment;
import com.boitdroid.birdapp.payload.request.CommentRequest;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {

    PagedResponse<Comment> getAllComments(Long tweetId, int page, int size);

    ApiResponse addComment(CommentRequest commentRequest, Long tweetId, UserPrincipal userPrincipal);

    Comment getComment(Long tweetId, Long commentId);

    ApiResponse updateComment(Long tweetId, Long commentId, CommentRequest commentRequest, UserPrincipal userPrincipal);

    ApiResponse deleteComment(Long tweetId, Long commentId, UserPrincipal userPrincipal);

}
