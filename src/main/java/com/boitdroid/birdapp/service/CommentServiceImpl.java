package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.post.Comment;
import com.boitdroid.birdapp.model.post.Tweet;
import com.boitdroid.birdapp.model.role.RoleName;
import com.boitdroid.birdapp.model.user.User;
import com.boitdroid.birdapp.exception.AppException;
import com.boitdroid.birdapp.exception.ResourceNotFoundException;
import com.boitdroid.birdapp.exception.UnauthorizedException;
import com.boitdroid.birdapp.payload.request.CommentRequest;
import com.boitdroid.birdapp.repository.CommentRepository;
import com.boitdroid.birdapp.repository.TweetRepository;
import com.boitdroid.birdapp.repository.UserRepository;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.utils.AppUtils;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public PagedResponse<Comment> getAllComments(Long tweetId, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");

        Page<Comment> comments = commentRepository.findByTweetId(tweetId, pageable);

        return new PagedResponse<>(comments.getContent(), comments.getNumber(), comments.getSize(),
                comments.getTotalElements(), comments.getTotalPages(), comments.isLast());
    }

    @Override
    public ApiResponse addComment(CommentRequest commentRequest, Long tweetId, UserPrincipal userPrincipal) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + tweetId + " not found"));
        User user = userRepository.getUser(userPrincipal);
        Comment comment = new Comment(commentRequest.getBody());
        comment.setUser(user);
        comment.setTweet(tweet);
        comment.setUsername(userPrincipal.getUsername());
        commentRepository.save(comment);

        return new ApiResponse(Boolean.TRUE, "Comment added successfully");

    }

    @Override
    public Comment getComment(Long tweetId, Long commentId) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + tweetId + " not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + commentId + " not found"));
        if (comment.getTweet().getId().equals(tweet.getId())) {
            return comment;
        }

        throw new AppException("Comment does not belong to this post");
    }

    @Override
    public ApiResponse updateComment(Long tweetId, Long commentId, CommentRequest commentRequest, UserPrincipal userPrincipal) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + tweetId + " not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + commentId + " not found"));

        if (!comment.getTweet().getId().equals(tweet.getId())) {
            throw new AppException("Comment does not belong to the post");
        }

        if (comment.getUser().getId().equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            comment.setBody(commentRequest.getBody());
            commentRepository.save(comment);
            return new ApiResponse(Boolean.TRUE, "Comment updated successfully");
        }

        throw new UnauthorizedException("You don't have permission to edit this comment");
    }

    @Override
    public ApiResponse deleteComment(Long tweetId, Long commentId, UserPrincipal userPrincipal) {
        Tweet tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + tweetId + " not found"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + commentId + " not found"));

        if (!comment.getTweet().getId().equals(tweet.getId())) {
            return new ApiResponse(Boolean.FALSE, "Comment does not belong to this post");
        }

        if (comment.getUser().getId().equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            commentRepository.deleteById(comment.getId());
            return new ApiResponse(Boolean.TRUE, "You successfully deleted comment");
        }

        throw new UnauthorizedException("You don't have permission to delete this comment");
    }
}
