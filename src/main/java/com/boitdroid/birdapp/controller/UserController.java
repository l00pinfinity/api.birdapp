package com.boitdroid.birdapp.controller;

import com.boitdroid.birdapp.data.models.Users;
import com.boitdroid.birdapp.data.payload.request.ForgotPasswordRequest;
import com.boitdroid.birdapp.data.payload.request.LoginRequest;
import com.boitdroid.birdapp.data.payload.request.SignupRequest;
import com.boitdroid.birdapp.data.payload.response.APIResponse;
import com.boitdroid.birdapp.data.payload.response.JWTAuthResponse;
import com.boitdroid.birdapp.data.payload.response.UsersResponse;
import com.boitdroid.birdapp.exceptions.AppException;
import com.boitdroid.birdapp.services.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UsersService usersService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${app.jwtExpirationInMs}")
    private int jwtExpirationInMs;

    @PostMapping("/auth/signin")
    public ResponseEntity<Object> accountLogin(@Valid @RequestBody LoginRequest loginRequest){

        try{
            return ResponseEntity.ok(new JWTAuthResponse(usersService.accountLogin(loginRequest),"Bearer",jwtExpirationInMs));
        }catch (AppException e){
            return ResponseEntity.ok(new APIResponse(Boolean.FALSE,e.getMessage()));
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody SignupRequest signupRequest){
        try{
            usersService.createAccount(signupRequest);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentContextPath().path("/api/users/{username}")
                    .buildAndExpand(signupRequest.getUsername()).toUri();
            return ResponseEntity.created(location).body(new APIResponse(true,"User registered successfully"));
        }catch (AppException e){
            return ResponseEntity.ok(new APIResponse(false,e.getMessage()));
        }
    }

    @PostMapping("/auth/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email){
        try{
            Optional<Users> byEmail = usersService.findByEmail(email);

            if (byEmail.isEmpty()){
                return ResponseEntity.ok(new APIResponse(Boolean.FALSE,"There is no account with an email address"));
            }else{
                Users user = byEmail.get();
                String genUUId = UUID.randomUUID().toString();
                user.setResetToken(genUUId);

                //save token
                usersService.saveUser(user);

                //Send user token via Email

                return ResponseEntity.ok(new APIResponse(Boolean.TRUE,"We have sent a password reset token to " + user.getEmail()));
            }
        }catch (AppException e){
            return ResponseEntity.ok(new APIResponse(Boolean.FALSE,e.getMessage()));
        }
    }

    @PostMapping("/auth/reset")
    public ResponseEntity<?> setNewPassword(@RequestParam("token") String token, @RequestBody ForgotPasswordRequest forgotPasswordRequest){
        Optional<Users> user = usersService.findByResetToken(token);

        if (user.isEmpty()){
            return ResponseEntity.ok(new APIResponse(Boolean.FALSE,"The password reset link is invalid."));
        }else {
            Users users = user.get();

            users.setPassword(passwordEncoder.encode(forgotPasswordRequest.getPassword()));
            users.setResetToken(null);

            usersService.saveUser(users);

            //Send user password reset confirmation email

            return ResponseEntity.ok(new APIResponse(Boolean.TRUE,"Your password has been successfully reset."));
        }

    }

    @GetMapping("/users/ba/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable("username") String username){
        Optional<Users> user = usersService.findByUsername(username);
        if (user.isEmpty()){
            return ResponseEntity.ok(new APIResponse(Boolean.FALSE,"No account with username provided"));
        }else {
            Users users = user.get();
            return ResponseEntity.ok(new UsersResponse(Boolean.TRUE,"User with username available",users));
        }
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUserById(@PathVariable("id") Long id){
        try{
            usersService.accountDelete(id);
            return ResponseEntity.ok(new APIResponse(Boolean.TRUE, "Account with id: " + id + " deleted successfully"));
        }catch (Exception e){
            return ResponseEntity.ok(new APIResponse(Boolean.FALSE,e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN','ROLE_MODERATOR')")
    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<Users> users = usersService.allUsers();
            logger.info("Admin fetching al users available");
            return ResponseEntity.ok(new UsersResponse(Boolean.TRUE,"Users available",users));
        } catch (Exception e) {
            return ResponseEntity.ok(new APIResponse(Boolean.FALSE,e.getMessage()));
        }
    }

}

