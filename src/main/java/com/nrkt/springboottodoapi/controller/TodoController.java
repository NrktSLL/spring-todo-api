package com.nrkt.springboottodoapi.controller;

import com.nrkt.springboottodoapi.payload.request.TodoRequestDTO;
import com.nrkt.springboottodoapi.payload.response.TodoResponseDTO;
import com.nrkt.springboottodoapi.service.TodoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/v1/todolist")
@Tag(name = "todo", description = "Todo Service")
@SecurityRequirement(name = "Bearer Auth")
public class TodoController {

    TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add Todo")
    public TodoResponseDTO addTodo(@RequestBody @Valid TodoRequestDTO todoRequestDTO) {
        return todoService.addTodo(todoRequestDTO);
    }

    @PutMapping("/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit Todo")
    public TodoResponseDTO editTodo(@PathVariable String todoId, @RequestBody @Valid TodoRequestDTO todoRequestDTO) {
        return todoService.editTodo(todoId, todoRequestDTO);
    }

    @DeleteMapping("/todo/{todoId}/user/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove Todo")
    public void deleteTodo(@PathVariable String todoId,
                           @PathVariable String userId) {
        todoService.removeTodo(userId, todoId);
    }

    @GetMapping("/todo/{todoId}/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get Todo")
    public TodoResponseDTO getTodo(@PathVariable String todoId,
                                   @PathVariable String userId) {
        return todoService.getUserTodo(userId, todoId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get All Todo")
    public List<TodoResponseDTO> getAllUserTodo(@PathVariable String userId) {
        return todoService.getUserTodoList(userId);
    }

    @PutMapping("/todo/{todoId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Complete To Todo")
    public TodoResponseDTO completeTodo(@PathVariable String todoId) {
        return todoService.todoComplete(todoId);
    }
}

