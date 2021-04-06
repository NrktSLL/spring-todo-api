package com.nrkt.springboottodoapi.controller;

import com.nrkt.springboottodoapi.payload.request.UserRequestDTO;
import com.nrkt.springboottodoapi.payload.response.UserResponseDTO;
import com.nrkt.springboottodoapi.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("v1/users")
@Tag(name = "users", description = "User Service")
public class UserController {

    UserService userService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Sign Up")
    public UserResponseDTO signUp(@RequestBody @Valid UserRequestDTO user) {
        return userService.addUser(user);
    }

    @PutMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Edit User")
    @SecurityRequirement(name = "Bearer Auth")
    public UserResponseDTO editUser(@RequestBody @Valid UserRequestDTO user, @PathVariable String userId) {
        return userService.editUser(userId, user);
    }

    @DeleteMapping("/{userId}/user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove User")
    @SecurityRequirement(name = "Bearer Auth")
    public void deleteUser(@PathVariable String userId) {
        userService.removeUser(userId);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get User")
    @SecurityRequirement(name = "Bearer Auth")
    public UserResponseDTO getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }
}
