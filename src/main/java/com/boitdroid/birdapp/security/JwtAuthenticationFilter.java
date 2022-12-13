package com.boitdroid.birdapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenService tokenProvider;

    @Autowired
    UserDetailService userDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = getJwtFromRequest(request);
            if (Boolean.TRUE.equals(tokenProvider.validateToken(jwt))) {
                setSecurityContext(new WebAuthenticationDetailsSource().buildDetails(request), jwt);
            }

            if (StringUtils.hasText(jwt) && Boolean.TRUE.equals(tokenProvider.validateToken(jwt))){
                String username = tokenProvider.getUsernameFromToken(jwt);

                UserDetails userDetails = userDetailService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            e.printStackTrace();
//                        logger.info("Could not set user authentication in security context " + e.getMessage());
        }
        filterChain.doFilter(request,response);
    }

    private void setSecurityContext(WebAuthenticationDetails buildDetails, String jwt) {
        final String username = tokenProvider.getUsernameFromToken(jwt);
        final String remoteAddress = buildDetails.getRemoteAddress();
        final String sessionId = buildDetails.getSessionId();
        final UserDetails userDetails = userDetailService.loadUserByUsername(username);
        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        authenticationToken.setDetails(userDetails);
        authenticationToken.setDetails(remoteAddress);
        authenticationToken.setDetails(sessionId);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7,bearerToken.length());
        }
        return null;
    }
}
