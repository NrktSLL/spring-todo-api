package com.nrkt.springboottodoapi.security.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.nrkt.springboottodoapi.security.user.UserDetailsServiceImpl;
import com.nrkt.springboottodoapi.security.util.jwt.TokenManager;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AuthTokenFilter extends OncePerRequestFilter {

    UserDetailsServiceImpl userDetailsService;
    TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = verifyHeader(request);
        if (token == null) return null;

        var userDetails = getUser(token);

        var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        return authentication;
    }

    private String verifyHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    private UserDetails getUser(String token) {
        DecodedJWT decodedJWT = tokenManager.verifyJwtToken(token);

        String userName = decodedJWT.getSubject();
        return userDetailsService.loadUserByUsername(userName);
    }
}
