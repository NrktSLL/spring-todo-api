package com.nrkt.springboottodoapi.service;

import com.nrkt.springboottodoapi.payload.request.LoginDTO;
import com.nrkt.springboottodoapi.payload.response.LoginResponseDTO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SessionService {

    LoginResponseDTO login(LoginDTO loginDTO);

    void logout(HttpServletRequest request, HttpServletResponse response);
}
