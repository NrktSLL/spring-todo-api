package com.nrkt.springboottodoapi.decarator;

import com.nrkt.springboottodoapi.domain.User;
import com.nrkt.springboottodoapi.mapper.UserMapper;
import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public abstract class UserMapperDecorator implements UserMapper {

    @Setter(onMethod = @__({@Autowired}))
    private UserMapper userMapper;

    @Setter(onMethod = @__({@Autowired}))
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public User userDtoToUser(UserRequestDTO userRequestDTO) {
        var user = userMapper.userDtoToUser(userRequestDTO);
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setUsername(userRequestDTO.getName().concat("#").concat(RandomStringUtils.randomAlphanumeric(6)));

        return user;
    }
}
