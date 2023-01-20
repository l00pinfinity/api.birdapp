package com.boitdroid.birdapp.controller;

import com.boitdroid.birdapp.exception.AppException;
import com.boitdroid.birdapp.model.user.User;
import com.boitdroid.birdapp.payload.request.ForgotPasswordRequest;
import com.boitdroid.birdapp.payload.request.SignInRequest;
import com.boitdroid.birdapp.payload.request.SignUpRequest;
import com.boitdroid.birdapp.payload.response.ApiResponse;
import com.boitdroid.birdapp.payload.response.JwtAuthenticationResponse;
import com.boitdroid.birdapp.security.JwtTokenProvider;
import com.boitdroid.birdapp.service.UserService;
import com.boitdroid.birdapp.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody SignInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequest.getUsernameOrEmail(), signInRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        User newUser = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),signUpRequest.getPassword());
        User user = userService.addUser(newUser);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        try{
            Optional<User> byEmail = userService.findByEmail(email);

            if (byEmail.isEmpty()){
                return ResponseEntity.ok(new ApiResponse(Boolean.FALSE,"There is no account with an email address"));
            }else{
                User user = byEmail.get();
                String resetToken = AppUtils.generateToken();
                user.setResetToken(resetToken);

                //save token
                userService.saveUser(user);

                //Send user token via Email

                return ResponseEntity.ok(new ApiResponse(Boolean.TRUE,"We have sent a password reset token to " + user.getEmail()));
            }
        }catch (AppException e){
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE,e.getMessage()));
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<?> setNewPassword(@RequestParam("token") String token, @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        Optional<User> user = userService.findByResetToken(token);

        if (user.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, "The password reset link is invalid."));
        } else {
            User users = user.get();

            users.setPassword(passwordEncoder.encode(forgotPasswordRequest.getPassword()));
            users.setResetToken(null);

            userService.saveUser(users);

            //Send user password reset confirmation email

            return ResponseEntity.ok(new ApiResponse(Boolean.TRUE, "Your password has been successfully reset."));
        }
    }
}
