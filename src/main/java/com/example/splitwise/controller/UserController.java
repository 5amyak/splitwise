package com.example.splitwise.controller;

import com.example.splitwise.dto.UserDto;
import com.example.splitwise.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserDto userDto) {
        try {
            userDto = userService.registerUser(userDto);

            log.info("User successfully registered with user id :: {}", userDto.getUserId());
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(userDto);
        } catch (Exception e) {
            log.error("Failed to register user due to {}", e.getMessage());
            throw e;
        }
    }
}
