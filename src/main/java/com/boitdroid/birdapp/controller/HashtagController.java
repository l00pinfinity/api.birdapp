package com.boitdroid.birdapp.controller;

import com.boitdroid.birdapp.model.post.Tag;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import com.boitdroid.birdapp.service.HashtagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trends")
@CrossOrigin(origins = "*")
public class HashtagController {

    @Autowired
    private HashtagService hashtagService;

    @GetMapping
    public ResponseEntity<PagedResponse<Tag>> trendsForYou(@RequestParam(value = "size", required = true, defaultValue = "10") Integer size) {
        PagedResponse<Tag> response = hashtagService.trendsForYou(size);

        return new ResponseEntity(response, HttpStatus.OK);
    }
}
