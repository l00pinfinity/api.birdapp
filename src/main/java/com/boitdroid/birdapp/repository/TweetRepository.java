package com.boitdroid.birdapp.repository;

import com.boitdroid.birdapp.model.post.Tweet;
import com.boitdroid.birdapp.model.post.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet,Long> {

    Page<Tweet> findByCreatedBy(Long userId, Pageable pageable);

    @Query("SELECT t FROM Tweet t WHERE t.tags IN :tags")
    Page<Tweet> findByTags(@Param("tags") List<Tag> tags, Pageable pageable);

    Long countByCreatedBy(Long userId);
}
