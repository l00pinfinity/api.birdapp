package com.boitdroid.birdapp.repository;

import com.boitdroid.birdapp.model.post.Tag;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);

    List<Tag> findTopByOrderByCountDesc(Pageable pageable);
}
