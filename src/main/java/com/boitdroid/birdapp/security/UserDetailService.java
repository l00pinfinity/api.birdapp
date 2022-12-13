package com.boitdroid.birdapp.security;

import com.boitdroid.birdapp.data.models.Users;
import com.boitdroid.birdapp.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    UsersRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        //login with email or username
        Users user = userRepository.findByEmailOrUsername(usernameOrEmail,usernameOrEmail).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail)
        );

        return UserPrincipal.create(user);
    }

    //This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id){
        Users user = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));
        return UserPrincipal.create(user);
    }
}
