package com.nrkt.springboottodoapi.service;

import com.nrkt.springboottodoapi.payload.request.TodoRequestDTO;
import com.nrkt.springboottodoapi.payload.response.TodoResponseDTO;

import java.util.List;

public interface TodoService {
    TodoResponseDTO addTodo(TodoRequestDTO todoRequestDTO);

    TodoResponseDTO editTodo(String todoId, TodoRequestDTO todoRequestDTO);

    void removeTodo(String userId, String todoId);

    TodoResponseDTO getUserTodo(String userId, String todoId);

    TodoResponseDTO todoComplete(String todoId);

    List<TodoResponseDTO> getUserTodoList(String userId);
}
