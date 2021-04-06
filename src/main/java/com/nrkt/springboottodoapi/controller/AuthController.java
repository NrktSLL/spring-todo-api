package com.nrkt.springboottodoapi.controller;

import com.nrkt.springboottodoapi.payload.request.LoginDTO;
import com.nrkt.springboottodoapi.payload.response.LoginResponseDTO;
import com.nrkt.springboottodoapi.service.SessionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("v1/auth")
@Tag(name = "auth", description = "Authentication  Service")
public class AuthController {

    SessionService sessionService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponseDTO login(@RequestBody @Valid LoginDTO login) {
        return sessionService.login(login);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        sessionService.logout(httpServletRequest, httpServletResponse);
    }
}
