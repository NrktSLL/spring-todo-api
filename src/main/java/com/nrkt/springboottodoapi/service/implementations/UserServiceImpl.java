package com.nrkt.springboottodoapi.service.implementations;

import com.couchbase.client.core.error.UserNotFoundException;
import com.nrkt.springboottodoapi.domain.User;
import com.nrkt.springboottodoapi.exception.MailAlreadyExistException;
import com.nrkt.springboottodoapi.mapper.UserMapper;
import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import com.nrkt.springboottodoapi.payload.response.UserResponseDTO;
import com.nrkt.springboottodoapi.repository.UserRepository;
import com.nrkt.springboottodoapi.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    UserMapper userMapper;

    @Override
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail()).equals(true))
            throw new MailAlreadyExistException("mail already exist: " + userRequestDTO.getEmail());

        var newUser = userMapper.userDtoToUser(userRequestDTO);
        userRepository.save(newUser);
        return userMapper.userToUserResponse(newUser);
    }

    @Override
    public UserResponseDTO editUser(String id, UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail()).equals(true))
            throw new MailAlreadyExistException("mail already exist: " + userRequestDTO.getEmail());

        var exitUser = getUserById(id);
        var user = userMapper.userDtoToUser(userRequestDTO);
        user.setId(exitUser.getId());
        user = userRepository.save(user);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public void removeUser(String id) {
        userRepository.delete(getUserById(id));
    }

    @Override
    public UserResponseDTO getUser(String id) {
        var user = getUserById(id);
        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User Service", "There is no user for this username: " + username));

        return userMapper.userToUserResponse(user);
    }

    @Override
    public UserResponseDTO findUserByEmail(String mail) {
        var user = userRepository
                .findByEmail(mail)
                .orElseThrow(() -> new UserNotFoundException("User Service", "There is no user for this email: " + mail));
        return userMapper.userToUserResponse(user);
    }

    private User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User Service", "There is no user for this id: " + id));
    }
}
