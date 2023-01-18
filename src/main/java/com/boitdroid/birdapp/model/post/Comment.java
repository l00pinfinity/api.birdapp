package com.boitdroid.birdapp.model.post;

import com.boitdroid.birdapp.model.audit.UserAudit;
import com.boitdroid.birdapp.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "comments")
public class Comment extends UserAudit {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotBlank
    @Size(min = 4, max = 50)
    private String username;

    @Column(name = "body")
    @NotBlank
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tweet_id")
    private Tweet tweet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(@NotBlank String body) {
        this.body = body;
    }

    @JsonIgnore
    public Tweet getTweet() {
        return tweet;
    }

    @JsonIgnore
    public User getUser() {
        return user;
    }
}
