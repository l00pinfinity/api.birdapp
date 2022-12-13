package com.boitdroid.birdapp.repository;

import com.boitdroid.birdapp.data.models.Tweets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TweetsRepository extends JpaRepository<Tweets,Long> {
}
