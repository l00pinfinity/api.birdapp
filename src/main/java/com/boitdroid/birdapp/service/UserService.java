package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.user.User;
import com.boitdroid.birdapp.payload.request.InfoRequest;
import com.boitdroid.birdapp.payload.request.UserIdentityAvailability;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.UserProfile;
import com.boitdroid.birdapp.payload.response.UserSummary;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    User addUser(User user);

    UserSummary getCurrentUser(UserPrincipal currentUser);

    UserIdentityAvailability checkUsernameAvailability(String username);

    UserIdentityAvailability checkEmailAvailability(String email);

    UserProfile getUserProfile(String username);

    User updateUser(User newUser, String username, UserPrincipal currentUser);

    ApiResponse deleteUser(String username, UserPrincipal currentUser);

    ApiResponse giveAdmin(String username);

    ApiResponse removeAdmin(String username);

    UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest);

    Optional<User> findByResetToken(String resetToken);

    Optional<User> findByEmail(String email);

    void saveUser(User user);

}
