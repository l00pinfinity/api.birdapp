package com.boitdroid.birdapp.model.post;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.boitdroid.birdapp.model.audit.UserAudit;
import com.boitdroid.birdapp.model.user.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tweets")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tweet extends UserAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "body", length = 65555)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "tweet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tweet_tag", joinColumns = @JoinColumn(name = "tweet_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private List<Tag> tags;

    public Tweet() {
    }

    public Tweet(String body, User user, List<Tag> tags) {
        this.body = body;
        this.user = user;
        this.tags = tags;
    }

    public Tweet(Long id, String body, User user, List<Comment> comments, List<Tag> tags) {
        this.id = id;
        this.body = body;
        this.user = user;
        this.comments = comments;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tweet tweet = (Tweet) o;
        return Objects.equals(id, tweet.id) && Objects.equals(body, tweet.body) && Objects.equals(user, tweet.user) && Objects.equals(comments, tweet.comments) && Objects.equals(tags, tweet.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, body, user, comments, tags);
    }
}
