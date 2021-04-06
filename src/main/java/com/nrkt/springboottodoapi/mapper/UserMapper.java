package com.nrkt.springboottodoapi.mapper;

import com.nrkt.springboottodoapi.decarator.UserMapperDecorator;
import com.nrkt.springboottodoapi.domain.User;
import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import com.nrkt.springboottodoapi.payload.response.UserResponseDTO;
import org.mapstruct.DecoratedWith;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@DecoratedWith(UserMapperDecorator.class)
public interface UserMapper {

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    User userDtoToUser(UserRequestDTO userRequestDTO);

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    UserResponseDTO userToUserResponse(User user);
}
