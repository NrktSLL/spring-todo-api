package com.nrkt.springboottodoapi.mapper;

import com.nrkt.springboottodoapi.domain.Todo;
import com.nrkt.springboottodoapi.payload.request.TodoRequestDTO;
import com.nrkt.springboottodoapi.payload.response.TodoResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TodoMapper {

    @Mapping(target = "completed", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "id", ignore = true)
    Todo todoRequestToTodo(TodoRequestDTO todoRequestDTO);

    TodoResponseDTO todoToTodoResponse(Todo todo);

    TodoRequestDTO todoToTodoRequest(Todo todo);
}
