package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.post.Tag;
import com.boitdroid.birdapp.model.post.Tweet;
import com.boitdroid.birdapp.payload.response.PagedResponse;
import com.boitdroid.birdapp.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class HashtagServiceImpl implements HashtagService{

    @Autowired
    private TagRepository tagRepository;

    @Override
    public PagedResponse<Tag> trendsForYou(int size) {
        Pageable pageable = PageRequest.of(0,size, Sort.Direction.DESC,"count");
        Page<Tag> hashtags = tagRepository.findAll(pageable);

        List<Tag> content = hashtags.getNumberOfElements() == 0 ? Collections.emptyList() : hashtags.getContent();
        return new PagedResponse<>(content,hashtags.getNumber(),hashtags.getSize(),hashtags.getTotalElements(), hashtags.getTotalPages(), hashtags.isLast());
    }
}
