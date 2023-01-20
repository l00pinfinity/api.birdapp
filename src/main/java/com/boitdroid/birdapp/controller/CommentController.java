package com.boitdroid.birdapp.controller;

import com.boitdroid.birdapp.model.post.Comment;
import com.boitdroid.birdapp.payload.request.CommentRequest;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import com.boitdroid.birdapp.security.CurrentUser;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/tweets/{tweetId}/comments")
@CrossOrigin(origins = "*")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<PagedResponse<Comment>> getAllComments(@PathVariable(name = "tweetId") Long tweetId,
                                                                 @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                                 @RequestParam(name = "size", required = false, defaultValue = "30") Integer size) {
        PagedResponse<Comment> allComments = commentService.getAllComments(tweetId, page, size);

        return new ResponseEntity< >(allComments, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<ApiResponse> addComment(@Valid @RequestBody CommentRequest commentRequest,
                                              @PathVariable(name = "tweetId") Long tweetId, @CurrentUser UserPrincipal userPrincipal) {
        ApiResponse apiResponse = commentService.addComment(commentRequest, tweetId, userPrincipal);

        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Comment> getComment(@PathVariable(name = "tweetId") Long tweetId,
                                              @PathVariable(name = "id") Long id) {
        Comment comment = commentService.getComment(tweetId, id);

        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> updateComment(@PathVariable(name = "tweetId") Long tweetId,
                                                 @PathVariable(name = "id") Long id, @Valid @RequestBody CommentRequest commentRequest,
                                                 @CurrentUser UserPrincipal currentUser) {
        ApiResponse apiResponse = commentService.updateComment(tweetId, id, commentRequest, currentUser);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "tweetId") Long tweetId,
                                                     @PathVariable(name = "id") Long id, @CurrentUser UserPrincipal currentUser) {

        ApiResponse response = commentService.deleteComment(tweetId, id, currentUser);

        HttpStatus status = response.getSuccess() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(response, status);
    }

}
