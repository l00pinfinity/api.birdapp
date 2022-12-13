package com.boitdroid.birdapp.data.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),@UniqueConstraint(columnNames = {"email"})})
public class Users {
    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private long id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    @Column(name = "email")
    @NotEmpty
    @Email
    @JsonIgnore
    private String email;

    @Column(name = "username")
    @NotEmpty
    private String username;

    @Column(name = "password")
    @NotEmpty
    @Size(max = 99)
    @JsonIgnore
    private String password;

    @Column(name = "reset_token")
    @JsonIgnore
    private String resetToken;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tweets",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "tweet_id"))
    private Set<Tweets> tweets = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @NotEmpty
    @JsonIgnore
    private Set<Roles> roles = new HashSet<>();
}
