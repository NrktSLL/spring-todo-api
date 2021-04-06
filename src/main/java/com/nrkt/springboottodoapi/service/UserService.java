package com.nrkt.springboottodoapi.service;

import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import com.nrkt.springboottodoapi.payload.response.UserResponseDTO;

public interface UserService {
    UserResponseDTO addUser(UserRequestDTO userRequestDTO);

    UserResponseDTO editUser(String id, UserRequestDTO userRequestDTO);

    void removeUser(String id);

    UserResponseDTO getUser(String id);

    UserResponseDTO findUserByEmail(String mail);

    UserResponseDTO getUserByUsername(String username);
}
