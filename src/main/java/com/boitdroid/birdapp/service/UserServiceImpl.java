package com.boitdroid.birdapp.service;

import com.boitdroid.birdapp.model.role.Role;
import com.boitdroid.birdapp.model.role.RoleName;
import com.boitdroid.birdapp.model.user.User;
import com.boitdroid.birdapp.exception.*;
import com.boitdroid.birdapp.payload.request.InfoRequest;
import com.boitdroid.birdapp.payload.request.UserIdentityAvailability;
import com.boitdroid.birdapp.repository.TweetRepository;
import com.boitdroid.birdapp.repository.RoleRepository;
import com.boitdroid.birdapp.repository.UserRepository;
import com.boitdroid.birdapp.security.UserPrincipal;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.UserProfile;
import com.boitdroid.birdapp.payload.response.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    TweetRepository tweetRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User addUser(User user) {
        if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()))) {
            throw new BadRequestException("It appears that the username you entered is already taken");
        }

        if (Boolean.TRUE.equals(userRepository.existsByEmail(user.getEmail()))) {
            throw new BadRequestException("It appears that the email you entered is already in use");
        }

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("Role not set for user")));
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public UserSummary getCurrentUser(UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername());
    }

    @Override
    public UserIdentityAvailability checkUsernameAvailability(String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @Override
    public UserIdentityAvailability checkEmailAvailability(String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @Override
    public UserProfile getUserProfile(String username) {
        User user = userRepository.getUserByName(username);

        Long postCount = tweetRepository.countByCreatedBy(user.getId());

        return new UserProfile(user.getId(), user.getUsername(),
                user.getCreatedAt(), user.getEmail(),postCount);
    }

    @Override
    public User updateUser(User newUser, String username, UserPrincipal currentUser) {
        User user = userRepository.getUserByName(username);
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            user.setPassword(passwordEncoder.encode(newUser.getPassword()));
            return userRepository.save(user);
        }
        throw new UnauthorizedException("You don't have permission to update profile of: " + username);
    }

    @Override
    public ApiResponse deleteUser(String username, UserPrincipal currentUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found"));
        if (!user.getId().equals(currentUser.getId()) || !currentUser.getAuthorities()
                .contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            throw new UnauthorizedException("You don't have permission to delete profile of: " + username);
        }
        userRepository.deleteById(user.getId());

        return new ApiResponse(Boolean.TRUE, "You successfully deleted profile of: " + username);
    }

    @Override
    public ApiResponse giveAdmin(String username) {
        User user = userRepository.getUserByName(username);

        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN).orElseThrow(()-> new AppException("User role not set")));
        roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(()-> new AppException("User role not set")));
        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE,"You gave ADMIN role to user: " + username);
    }

    @Override
    public ApiResponse removeAdmin(String username) {
        User user = userRepository.getUserByName(username);
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User role not set")));
        user.setRoles(roles);
        userRepository.save(user);
        return new ApiResponse(Boolean.TRUE, "You gave ADMIN role to user: " + username);
    }

    @Override
    public UserProfile setOrUpdateInfo(UserPrincipal currentUser, InfoRequest infoRequest) {
        User user = userRepository.findByUsername(currentUser.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User with username: " + currentUser.getUsername()));
        if (user.getId().equals(currentUser.getId())
                || currentUser.getAuthorities().contains(new SimpleGrantedAuthority(RoleName.ROLE_ADMIN.toString()))) {
            User updatedUser = userRepository.save(user);

            Long postCount = tweetRepository.countByCreatedBy(updatedUser.getId());

            return new UserProfile(updatedUser.getId(),updatedUser.getUsername(),updatedUser.getCreatedAt(),updatedUser.getEmail(),postCount);
        }
        throw new AccessDeniedException("You don't have permission to update users profile");
    }

    @Override
    public Optional<User> findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
