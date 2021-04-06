package com.nrkt.springboottodoapi.service.implementations;

import com.nrkt.springboottodoapi.domain.Todo;
import com.nrkt.springboottodoapi.exception.TodoNotFoundException;
import com.nrkt.springboottodoapi.mapper.TodoMapper;
import com.nrkt.springboottodoapi.payload.request.TodoRequestDTO;
import com.nrkt.springboottodoapi.payload.response.TodoResponseDTO;
import com.nrkt.springboottodoapi.repository.TodoRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Todo Service Test")
class TodoServiceImplTest {

    private static final String Title = "DEFAULT_TITLE";
    private static final LocalDate Date = LocalDate.now();
    private static final String Description = "DEFAULT_DESCRIPTION";
    private static final String DefaultUserId = "Test";

    private TodoRequestDTO todoRequestDTO;
    private TodoResponseDTO todoResponseDTO;
    private Todo todoEntity;

    @Mock
    private TodoRepository todoRepository;

    @Mock
    private TodoMapper todoMapper;

    @InjectMocks
    private TodoServiceImpl todoService;


    @BeforeEach
    void setUp() {
        todoRequestDTO = TodoRequestDTO.builder()
                .title(Title)
                .date(Date)
                .description(Description)
                .userId(DefaultUserId)
                .build();

        todoEntity = Todo.builder()
                .id(UUID.randomUUID().toString())
                .title(Title)
                .date(Date)
                .description(Description)
                .completed(false)
                .userId(DefaultUserId)
                .build();

        todoResponseDTO = TodoResponseDTO.builder()
                .id(todoEntity.getId())
                .title(Title)
                .date(Date)
                .description(Description)
                .completed(true)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("Test Mock Todo Service Method and Todo Repository")
    void addTodo() {
        when(todoMapper.todoRequestToTodo(ArgumentMatchers.any(TodoRequestDTO.class))).thenReturn(todoEntity);
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoResponseDTO);
        when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(todoEntity);
        TodoResponseDTO response = todoService.addTodo(todoRequestDTO);

        verify(todoRepository, times(1)).save(todoEntity);

        assertAll("Verify the TODO Service Add Condition",
                () -> assertNotNull(response.getId()),
                () -> assertNotNull(response.getDate()),
                () -> assertNotNull(response.getTitle()),
                () -> assertNotNull(response.getCompleted())
        );
    }

    @Test
    @Order(2)
    @DisplayName("Test Mock Todo Service Edit Method and Todo Repository")
    void editTodo() {
        when(todoMapper.todoRequestToTodo(ArgumentMatchers.any(TodoRequestDTO.class))).thenReturn(todoEntity);
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoResponseDTO);
        when(todoRepository.findByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(todoEntity));
        when(todoRepository.save(ArgumentMatchers.any(Todo.class))).thenReturn(todoEntity);

        TodoResponseDTO responseCorrectUser = todoService.editTodo(todoEntity.getId(), todoRequestDTO);

        verify(todoRepository, times(1)).save(todoEntity);

        assertAll("Verify the TODO Service Edit Condition",
                () -> assertNotNull(responseCorrectUser.getId()),
                () -> assertNotNull(responseCorrectUser.getDate()),
                () -> assertNotNull(responseCorrectUser.getTitle()),
                () -> assertNotNull(responseCorrectUser.getCompleted())
        );
    }

    @Test
    @Order(3)
    @DisplayName("Test Mock Todo Service Remove Method and Todo Repository")
    void removeTodo() {
        when(todoRepository.findByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(todoEntity));
        todoService.removeTodo(DefaultUserId, todoEntity.getId());

        verify(todoRepository, times(1)).delete(todoEntity);
    }

    @Test
    @Order(4)
    @DisplayName("Test Mock Todo Service Get User's Todo Method")
    void getUserTodo() {
        when(todoMapper.todoToTodoResponse(ArgumentMatchers.any(Todo.class))).thenReturn(todoResponseDTO);
        when(todoRepository.findByIdAndUserId(ArgumentMatchers.any(String.class), ArgumentMatchers.any(String.class)))
                .thenReturn(Optional.of(todoEntity));

        TodoResponseDTO response = todoService.getUserTodo(DefaultUserId, todoEntity.getId());

        assertAll("Verify the TODO Service GetUser Condition",
                () -> assertNotNull(response.getId()),
                () -> assertNotNull(response.getDate()),
                () -> assertNotNull(response.getTitle()),
                () -> assertNotNull(response.getCompleted())
        );
    }

    @Test
    @Order(6)
    void TodoNonExistentTest() {
        String wrongTodoId = "wrong";
        when(todoRepository.findById(wrongTodoId)).thenThrow(TodoNotFoundException.class);

        assertAll("Verify the TOD Service Non-Existent Todo Condition",
                () -> assertThrows(TodoNotFoundException.class, () -> todoService.editTodo(wrongTodoId, todoRequestDTO)),
                () -> assertThrows(TodoNotFoundException.class, () -> todoService.removeTodo(DefaultUserId, wrongTodoId)),
                () -> assertThrows(TodoNotFoundException.class, () -> todoService.getUserTodo(DefaultUserId, wrongTodoId)),
                () -> assertThrows(TodoNotFoundException.class, () -> todoService.todoComplete(wrongTodoId))
        );
    }
}