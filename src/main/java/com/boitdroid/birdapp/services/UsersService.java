package com.boitdroid.birdapp.services;

import com.boitdroid.birdapp.data.models.Users;
import com.boitdroid.birdapp.data.payload.request.LoginRequest;
import com.boitdroid.birdapp.data.payload.request.SignupRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UsersService {

    Object createAccount(SignupRequest signupRequest);
    String accountLogin(LoginRequest loginRequest);
    void accountDelete(Long id);
    List<Users> allUsers();
    Optional<Users> findByEmail(String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByResetToken(String resetToken);
    void saveUser(Users user);
}
