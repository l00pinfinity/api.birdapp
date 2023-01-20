package com.boitdroid.birdapp.controller;

import com.boitdroid.birdapp.model.post.Tweet;
import com.boitdroid.birdapp.payload.request.TweetRequest;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import com.boitdroid.birdapp.security.CurrentUser;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tweets")
@CrossOrigin(origins = "*")
public class TweetController {
    @Autowired
    private TweetService tweetService;

    @GetMapping
    public ResponseEntity<PagedResponse<Tweet>> getAllPosts(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "30") Integer size) {
        PagedResponse<Tweet> response = tweetService.getAllPosts(page, size);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    // TODO: 1/16/2023 Add PostsByPlatform 

    @GetMapping("/tag/{id}")
    public ResponseEntity<PagedResponse<Tweet>> getPostsByTag(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "30") Integer size,
            @PathVariable(name = "id") Long id) {
        PagedResponse<Tweet> response = tweetService.getPostsByTag(id, page, size);

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> addPost(@Valid @RequestBody TweetRequest tweetRequest,
                                               @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = tweetService.addPost(tweetRequest, currentUser);

        return new ResponseEntity(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tweet> getPost(@PathVariable(name = "id") Long id) {
        Tweet tweet = tweetService.getPostById(id);
        return new ResponseEntity(tweet, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<Tweet> updatePost(@PathVariable(name = "id") Long id,
                                            @Valid @RequestBody TweetRequest newTweetRequest, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = tweetService.updatePost(id, newTweetRequest, currentUser);

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = tweetService.deletePost(id, currentUser);

        return new ResponseEntity(apiResponse, HttpStatus.OK);
    }
}
