package com.nrkt.springboottodoapi.service.implementations;

import com.nrkt.springboottodoapi.payload.request.LoginDTO;
import com.nrkt.springboottodoapi.payload.response.LoginResponseDTO;
import com.nrkt.springboottodoapi.security.util.jwt.TokenManager;
import com.nrkt.springboottodoapi.service.SessionService;
import com.nrkt.springboottodoapi.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class SessionServiceImpl implements SessionService {

    UserService userService;
    AuthenticationManager authenticationManager;
    TokenManager tokenManager;

    @Override
    @Cacheable("UserLogin")
    public LoginResponseDTO login(LoginDTO loginDTO) {
        var user = userService.findUserByEmail(loginDTO.getEmail());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), loginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenManager.generateJwtToken(authentication);

        return new LoginResponseDTO(jwt);
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) new SecurityContextLogoutHandler().logout(request, response, auth);
    }
}
