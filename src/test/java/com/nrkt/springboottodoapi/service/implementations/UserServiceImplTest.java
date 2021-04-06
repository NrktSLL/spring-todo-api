package com.nrkt.springboottodoapi.service.implementations;

import com.nrkt.springboottodoapi.domain.User;
import com.nrkt.springboottodoapi.mapper.UserMapper;
import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import com.nrkt.springboottodoapi.payload.response.UserResponseDTO;
import com.nrkt.springboottodoapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("User Service Test")
class UserServiceImplTest {

    private static final String DefaultUserName = "TestUserName";
    private static final String DefaultPassword = "Test_PASS";
    private static final String DefaultEmail = "test@gmail.com";
    private static final String DefaultName = "TEST";

    private User user;
    private UserResponseDTO userResponseDTO;
    private UserRequestDTO userRequestDTO;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(DefaultUserName)
                .password(DefaultPassword)
                .email(DefaultEmail)
                .name(DefaultName)
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .userId(UUID.randomUUID().toString())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();

        userRequestDTO = UserRequestDTO.builder()
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }

    @Test
    @DisplayName("Test Mock User Service Method and User Repository")
    void addUserTest() {
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDTO);
        when(userMapper.userDtoToUser(ArgumentMatchers.any(UserRequestDTO.class))).thenReturn(user);
        when(userRepository.existsByEmail(ArgumentMatchers.any(String.class))).thenReturn(false);
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.addUser(userRequestDTO);

        verify(userRepository, times(1)).save(user);

        assertAll("Verify the User Service Add Condition",
                () -> assertNotNull(response.getEmail()),
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getUserId())
        );
    }

    @Test
    @DisplayName("Test Mock User Service Edit Method and User Repository")
    void editUserTest() {
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDTO);
        when(userMapper.userDtoToUser(ArgumentMatchers.any(UserRequestDTO.class))).thenReturn(user);
        when(userRepository.existsByEmail(ArgumentMatchers.any(String.class))).thenReturn(false);
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(userRepository.save(ArgumentMatchers.any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.addUser(userRequestDTO);

        verify(userRepository, times(1)).save(user);

        assertAll("Verify the User Service Edit condition",
                () -> assertNotNull(response.getEmail()),
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getUserId())
        );
    }

    @Test
    @DisplayName("Test Mock User Service Remove Method and User Repository")
    void removeUserTest() {
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));

        userService.removeUser(user.getId());

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    @DisplayName("Test Mock User Service Get User Method and User Repository")
    void getUserTest() {
        when(userRepository.findById(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDTO);

        var response = userService.getUser(user.getId());

        assertAll("Verify the User Service Get User Condition",
                () -> assertNotNull(response.getEmail()),
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getUserId())
        );
    }

    @Test
    @DisplayName("Test Mock User Service Get User By Mail Method and User Repository")
    void findUserByEmailTest() {
        when(userRepository.findByEmail(ArgumentMatchers.any(String.class))).thenReturn(Optional.of(user));
        when(userMapper.userToUserResponse(ArgumentMatchers.any(User.class))).thenReturn(userResponseDTO);

        var response = userService.findUserByEmail(user.getEmail());

        verify(userRepository, times(1)).findByEmail(user.getEmail());

        assertAll("Verify and Find by Mail to the User Get User Condition",
                () -> assertNotNull(response.getEmail()),
                () -> assertNotNull(response.getUsername()),
                () -> assertNotNull(response.getUserId())
        );
    }
}