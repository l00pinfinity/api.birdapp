package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.post.Tag;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HashtagService {

    PagedResponse<Tag> trendsForYou(int size);
}
