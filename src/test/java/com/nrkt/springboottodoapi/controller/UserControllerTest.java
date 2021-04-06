package com.nrkt.springboottodoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import com.nrkt.springboottodoapi.payload.response.UserResponseDTO;
import com.nrkt.springboottodoapi.security.user.UserDetailsServiceImpl;
import com.nrkt.springboottodoapi.security.util.AuthEntryPoint;
import com.nrkt.springboottodoapi.security.util.jwt.TokenManager;
import com.nrkt.springboottodoapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("User Service Controller Test")
class UserControllerTest {

    private static final String DefaultUserName = "TestUserName";
    private static final String DefaultPassword = "Test_PASS";
    private static final String DefaultEmail = "test@gmail.com";
    private static final String DefaultName = "TEST";
    private static final String url = "/v1/users/";

    private UserRequestDTO userRequestDTO;
    private UserResponseDTO userResponseDTO;
    String userId = UUID.randomUUID().toString();

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    @MockBean
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    TokenManager tokenManager;

    @MockBean
    AuthEntryPoint authEntryPoint;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        userRequestDTO = UserRequestDTO.builder()
                .email(DefaultEmail)
                .password(DefaultPassword)
                .name(DefaultName)
                .build();

        userResponseDTO = UserResponseDTO.builder()
                .userId(userId)
                .username(DefaultName)
                .email(DefaultEmail)
                .build();
    }

    @Test
    void addUserControllerTest() throws Exception {
        String content = objectMapper.writeValueAsString(userRequestDTO);
        given(userService.addUser(userRequestDTO)).willReturn(userResponseDTO);

        MockHttpServletRequestBuilder builder = post(url.concat("signup"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        UserRequestDTO requestContent = objectMapper.readValue(content, UserRequestDTO.class);
        UserResponseDTO responseContent = objectMapper.readValue(response.getContentAsString(), UserResponseDTO.class);

        assertAll("Verify the TODO Controller POST condition",
                () -> assertEquals(201, response.getStatus()),
                () -> assertEquals(requestContent.getEmail(), responseContent.getEmail())
        );
    }

    @Test
    void editUserControllerTest() throws Exception {
        String content = objectMapper.writeValueAsString(userRequestDTO);
        given(userService.editUser(userId, userRequestDTO)).willReturn(userResponseDTO);

        MockHttpServletRequestBuilder builder = put(url.concat("user/").concat(userId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertAll("Verify the TODO Controller POST condition",
                () -> assertEquals(200, response.getStatus())
        );
    }

    @Test
    void getUserControllerTest() throws Exception {
        given(userService.getUser(userId)).willReturn(userResponseDTO);

        String uri = url.concat(userId);

        MockHttpServletRequestBuilder builder = get(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void deleteUserControllerTest() throws Exception {
        String uri = url.concat(userId).concat("/user");

        MockHttpServletRequestBuilder builder = delete(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(204, response.getStatus(), "Verify the TODO Controller DELETE condition");
    }
}