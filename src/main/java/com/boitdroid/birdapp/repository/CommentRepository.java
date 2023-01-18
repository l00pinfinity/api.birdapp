package com.boitdroid.birdapp.repository;
import com.boitdroid.birdapp.model.post.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByTweetId(Long postId, Pageable pageable);
}
