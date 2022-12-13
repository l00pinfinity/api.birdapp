package com.boitdroid.birdapp.services.impl;

import com.boitdroid.birdapp.data.models.RoleNames;
import com.boitdroid.birdapp.data.models.Roles;
import com.boitdroid.birdapp.data.models.Users;
import com.boitdroid.birdapp.data.payload.request.LoginRequest;
import com.boitdroid.birdapp.data.payload.request.SignupRequest;
import com.boitdroid.birdapp.exceptions.AppException;
import com.boitdroid.birdapp.exceptions.ResourceNotFoundException;
import com.boitdroid.birdapp.repository.RolesRepository;
import com.boitdroid.birdapp.repository.UsersRepository;
import com.boitdroid.birdapp.security.JwtTokenService;
import com.boitdroid.birdapp.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository userRepository;

    @Autowired
    RolesRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenService tokenProvider;

    @Override
    public Object createAccount(SignupRequest signupRequest) {
        if (Boolean.TRUE.equals(userRepository.existsByEmail(signupRequest.getEmail()))){
            throw new AppException("Email is already in use");
        }else if(Boolean.TRUE.equals(userRepository.existsByUsername(signupRequest.getUsername()))){
            throw new AppException("Username is already taken");
        }else {
            //Create new user after successful checks
            Users user = new Users();
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail().toLowerCase(Locale.ROOT));
            user.setUsername(signupRequest.getUsername().toLowerCase(Locale.ROOT));
            user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
            Roles userRole = roleRepository.findByName(RoleNames.ROLE_USER).orElseThrow(() -> new AppException("User Role not set"));
            user.setRoles(Collections.singleton(userRole));
            try {
                //Send user email
            }catch (Exception e){

            }
            return userRepository.save(user);
        }
    }

    @Override
    public String accountLogin(LoginRequest loginRequest) {
        try{
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail().toLowerCase(Locale.ROOT),loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            return tokenProvider.generateToken(authentication);
        }catch (AppException e){
            return e.getMessage();
        }
    }

    @Override
    public void accountDelete(Long id) {
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }else {
            throw new ResourceNotFoundException("User with " + id + " does not exist");
        }
    }

    @Override
    public List<Users> allUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<Users> findByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public void saveUser(Users user) {
        userRepository.save(user);
    }
}
