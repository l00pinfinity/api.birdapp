package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.post.Tweet;
import com.boitdroid.birdapp.model.post.Tag;
import com.boitdroid.birdapp.model.role.RoleName;
import com.boitdroid.birdapp.model.user.User;
import com.boitdroid.birdapp.exception.ResourceNotFoundException;
import com.boitdroid.birdapp.exception.UnauthorizedException;
import com.boitdroid.birdapp.payload.request.TweetRequest;
import com.boitdroid.birdapp.repository.TweetRepository;
import com.boitdroid.birdapp.repository.TagRepository;
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

import java.util.*;

@Service
public class TweetServiceImpl implements TweetService {

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TagRepository tagRepository;

    @Override
    public PagedResponse<Tweet> getAllPosts(int page, int size) {
        AppUtils.validatePageNumberAndSize(page,size);
        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<Tweet> posts = tweetRepository.findAll(pageable);

        List<Tweet> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content,posts.getNumber(),posts.getSize(),posts.getTotalElements(), posts.getTotalPages(), posts.isLast());
    }

    @Override
    public PagedResponse<Tweet> getPostsByCreatedBy(String username, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        User user = userRepository.getUserByName(username);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC);
        Page<Tweet> posts = tweetRepository.findByCreatedBy(user.getId(), pageable);

        List<Tweet> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),posts.getTotalPages(), posts.isLast());
    }

    @Override
    public PagedResponse<Tweet> getPostsByTag(String tagName, int page, int size) {
        AppUtils.validatePageNumberAndSize(page, size);
        Tag tag = tagRepository.findByName(tagName);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC,"createdAt");
        Page<Tweet> posts = tweetRepository.findByTags(Collections.singletonList(tag), pageable);

        List<Tweet> content = posts.getNumberOfElements() == 0 ? Collections.emptyList() : posts.getContent();

        return new PagedResponse<>(content, posts.getNumber(), posts.getSize(), posts.getTotalElements(),posts.getTotalPages(), posts.isLast());
    }

    @Override
    public ApiResponse updatePost(Long id, TweetRequest tweetRequest, UserPrincipal userPrincipal) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with ID not found"));
        if (tweet.getUser().getId().equals(userPrincipal.getId()) || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            tweet.setBody(tweetRequest.getBody());
            tweetRepository.save(tweet);
            return new ApiResponse(Boolean.TRUE, "Post updated successfully");
        }
        throw new UnauthorizedException("You don't have permission to edit this post");
    }

    @Override
    public ApiResponse deletePost(Long id, UserPrincipal userPrincipal) {
        Tweet tweet = tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with ID not found"));
        if (tweet.getUser().getId().equals(userPrincipal.getId())
                || userPrincipal.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            tweetRepository.deleteById(id);
            return new ApiResponse(Boolean.TRUE, "You successfully deleted post");
        }
        throw new UnauthorizedException("You don't have permission to delete this post");
    }

    @Override
    public ApiResponse addPost(TweetRequest tweetRequest, UserPrincipal currentUser) {
        User user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID: " + currentUser.getId() + " not found"));

        List<String> hashtags = AppUtils.hashtagsInTweet(tweetRequest.getBody());

        List<Tag> tags = new ArrayList<>();
        for (String hashtag : hashtags) {
            Optional<Tag> existingTag = Optional.ofNullable(tagRepository.findByName(hashtag));
            if(existingTag.isEmpty()){
                Tag tag = new Tag(hashtag);
                tagRepository.save(tag);
                tags.add(tag);
            }else{
                Tag tag = existingTag.get();
                tag.setCount(tag.getCount() + 1);
                tagRepository.save(tag);
            }
        }

        Tweet tweet = new Tweet();
        tweet.setBody(tweetRequest.getBody());
        tweet.setUser(user);
        tweet.setTags(tags);
        System.out.println(tweet);

        Tweet savedTweet = tweetRepository.save(tweet);

        List<String> tagNames = new ArrayList<>(savedTweet.getTags().size());

        for (Tag tag : savedTweet.getTags()) {
            tagNames.add(tag.getName());
        }

        return new ApiResponse(true, "Tweet added successfully");

    }

    @Override
    public Tweet getPostById(Long id) {
        return tweetRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post with ID: " + id + " not found"));
    }
}
