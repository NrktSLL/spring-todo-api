package com.nrkt.springboottodoapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrkt.springboottodoapi.payload.request.TodoRequestDTO;
import com.nrkt.springboottodoapi.payload.response.TodoResponseDTO;
import com.nrkt.springboottodoapi.security.user.UserDetailsServiceImpl;
import com.nrkt.springboottodoapi.security.util.AuthEntryPoint;
import com.nrkt.springboottodoapi.security.util.jwt.TokenManager;
import com.nrkt.springboottodoapi.service.TodoService;
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

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TodoController.class)
@AutoConfigureMockMvc(addFilters = false)
@DisplayName("Todo Service Controller Test")
class TodoControllerTest {

    private static final String Title = "DEFAULT_TITLE";
    private static final LocalDate Date = LocalDate.now();
    private static final String Description = "DEFAULT_DESCRIPTION";
    private static final String DefaultUserId = "Test";
    private static final String url = "/v1/todolist/";

    private TodoRequestDTO todoRequestDTO;
    private TodoResponseDTO todoResponseDTO;
    private String todoId;
    private String userId;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

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
        todoId = UUID.randomUUID().toString();
        userId = UUID.randomUUID().toString();

        todoRequestDTO = TodoRequestDTO.builder()
                .title(Title)
                .date(Date)
                .userId(DefaultUserId)
                .description(Description)
                .build();

        todoResponseDTO = TodoResponseDTO.builder()
                .id(todoId)
                .title(Title)
                .date(Date)
                .description(Description)
                .completed(false)
                .build();
    }

    @Test
    void addTodoServiceControllerTest() throws Exception {
        String content = objectMapper.writeValueAsString(todoRequestDTO);
        given(todoService.addTodo(todoRequestDTO)).willReturn(todoResponseDTO);

        MockHttpServletRequestBuilder builder = post(url)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        TodoRequestDTO requestContent = objectMapper.readValue(content, TodoRequestDTO.class);
        TodoResponseDTO responseContent = objectMapper.readValue(response.getContentAsString(), TodoResponseDTO.class);

        assertAll("Verify the TODO Controller POST condition",
                () -> assertEquals(201, response.getStatus()),
                () -> assertEquals(requestContent.getDescription(), responseContent.getDescription()),
                () -> assertEquals(requestContent.getTitle(), responseContent.getTitle())
        );
    }

    @Test
    void editTodoServiceControllerTest() throws Exception {
        String content = objectMapper.writeValueAsString(todoRequestDTO);
        given(todoService.editTodo(todoId, todoRequestDTO)).willReturn(todoResponseDTO);

        MockHttpServletRequestBuilder builder = put(url.concat(todoId))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        TodoRequestDTO requestContent = objectMapper.readValue(content, TodoRequestDTO.class);
        TodoResponseDTO responseContent = objectMapper.readValue(response.getContentAsString(), TodoResponseDTO.class);

        assertAll("Verify the TODO Controller PUT condition",
                () -> assertEquals(200, response.getStatus()),
                () -> assertEquals(requestContent.getDescription(), responseContent.getDescription()),
                () -> assertEquals(requestContent.getTitle(), responseContent.getTitle())
        );
    }

    @Test
    void getTodoServiceControllerTest() throws Exception {
        given(todoService.getUserTodo(userId, todoId)).willReturn(todoResponseDTO);

        String uri = url.concat("todo/").concat(todoId).concat("/user/").concat(DefaultUserId);

        MockHttpServletRequestBuilder builder = get(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void getAllUserTodoServiceControllerTest() throws Exception {
        MockHttpServletRequestBuilder builder = get(url.concat(DefaultUserId)).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void completeTodoServiceControllerTest() throws Exception {
        String uri = url.concat("todo/").concat(todoId);

        MockHttpServletRequestBuilder builder = put(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(200, response.getStatus(), "Verify the TODO Controller Get condition");
    }

    @Test
    void deleteTodoServiceControllerTest() throws Exception {
        String uri = url.concat("todo/").concat(todoId).concat("/user/").concat(userId);

        MockHttpServletRequestBuilder builder = delete(uri).contentType(MediaType.APPLICATION_JSON);

        MockHttpServletResponse response = mockMvc.perform(builder).andReturn().getResponse();

        assertEquals(204, response.getStatus(), "Verify the TODO Controller DELETE condition");
    }
}