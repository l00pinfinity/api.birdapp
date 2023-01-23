package com.boitdroid.birdapp.model.post;

import com.boitdroid.birdapp.model.audit.UserAudit;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "tags")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Hashtag extends UserAudit {

    private static final long serialVersionUID = -5298707266367331514L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "count")
    private int count;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tweet_hashtag", joinColumns = @JoinColumn(name = "hashtag_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tweet_id", referencedColumnName = "id"))
    private List<Tweet> tweets;

    public Hashtag(String name) {
        super();
        this.name = name;
    }

    public List<Tweet> getTweets() {
        return tweets == null ? null : new ArrayList<>(tweets);
    }

    public void setTweets(List<Tweet> tweets) {
        if (tweets == null) {
            this.tweets = null;
        } else {
            this.tweets = Collections.unmodifiableList(tweets);
        }
    }
}
