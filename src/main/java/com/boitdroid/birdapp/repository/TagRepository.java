package com.boitdroid.birdapp.repository;

import com.boitdroid.birdapp.model.post.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    Tag findByName(String name);
}
