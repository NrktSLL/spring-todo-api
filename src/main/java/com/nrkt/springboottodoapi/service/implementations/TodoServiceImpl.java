package com.nrkt.springboottodoapi.service.implementations;

import com.nrkt.springboottodoapi.domain.Todo;
import com.nrkt.springboottodoapi.exception.TodoNotFoundException;
import com.nrkt.springboottodoapi.mapper.TodoMapper;
import com.nrkt.springboottodoapi.payload.request.TodoRequestDTO;
import com.nrkt.springboottodoapi.payload.response.TodoResponseDTO;
import com.nrkt.springboottodoapi.repository.TodoRepository;
import com.nrkt.springboottodoapi.service.TodoService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class TodoServiceImpl implements TodoService {

    TodoRepository todoRepository;
    TodoMapper todoMapper;

    @Override
    public TodoResponseDTO addTodo(TodoRequestDTO todoRequestDTO) {
        var todo = todoMapper.todoRequestToTodo(todoRequestDTO);
        todo.setUserId(todoRequestDTO.getUserId());
        todo.setCompleted(false);
        todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    @CachePut("cacheUserTodo")
    public TodoResponseDTO editTodo(String todoId, TodoRequestDTO todoRequestDTO) {
        var exitTodo = findTodo(todoRequestDTO.getUserId(), todoId);

        var newTodoContent = todoMapper.todoRequestToTodo(todoRequestDTO);
        newTodoContent.setId(exitTodo.getId());
        newTodoContent = todoRepository.save(newTodoContent);

        return todoMapper.todoToTodoResponse(newTodoContent);
    }

    @Override
    @CacheEvict("cacheUserTodo")
    public void removeTodo(String userId, String todoId) {
        var todo = findTodo(todoId, userId);
        todoRepository.delete(todo);
    }

    @Override
    @Cacheable("cacheUserTodo")
    public TodoResponseDTO getUserTodo(String userId, String todoId) {
        var todo = findTodo(todoId, userId);
        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public TodoResponseDTO todoComplete(String todoId) {
        var todo = todoRepository.findById(todoId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
        todo.setCompleted(true);
        todo = todoRepository.save(todo);

        return todoMapper.todoToTodoResponse(todo);
    }

    @Override
    public List<TodoResponseDTO> getUserTodoList(String userId) {
        var todoList = todoRepository.findAllByUserId(userId);

        return todoList.stream()
                .map(todoMapper::todoToTodoResponse)
                .collect(Collectors.toList());
    }

    private Todo findTodo(String todoId, String userId) {
       return todoRepository.findByIdAndUserId(todoId, userId).orElseThrow(() -> new TodoNotFoundException("Todo can not found !"));
    }
}
