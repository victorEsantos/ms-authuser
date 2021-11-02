package com.ead.authuser.api.controllers;

import com.ead.authuser.api.dtos.UserDto;
import com.ead.authuser.service.UserAppService;
import com.ead.authuser.service.UserService;
import com.ead.authuser.service.command.RegisterUserCommand;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAppService service;


    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @Validated(UserDto.UserView.RegistrationPost.class)
                                               @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto) {

        //todo validação deve estar no handle, remover posteriormente
        if (userService.existsByUsername(dto.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already token!");
        }
        if (userService.existsByEmail(dto.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already token!");
        }

        var cmd = RegisterUserCommand
                .builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .cpf(dto.getCpf())
                .build();


        var user = service.handle(cmd);
        log.debug("User saved id: {}", user.getId());
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/").path(user.getId().toString()).build().toUri();

        return ResponseEntity.status(HttpStatus.CREATED).body(uri);
    }

    @GetMapping("/")
    public String index() {
        log.trace("TRACE");
        log.debug("debug");
        log.info("INfo");
        log.warn("warn");
        log.error("ERRORRRRRR");

        return "Logging...";
    }
}
