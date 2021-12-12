package com.ead.authuser.user.api.controllers;

import com.ead.authuser.user.api.dtos.UserDto;
import com.ead.authuser.user.domain.model.User;
import com.ead.authuser.user.service.UserAppService;
import com.ead.authuser.user.service.UserService;
import com.ead.authuser.user.service.command.DeleteUserCommand;
import com.ead.authuser.user.service.command.UpdateUserCommand;
import com.ead.authuser.user.service.command.UpdateUserImageCommand;
import com.ead.authuser.user.service.command.UpdateUserPasswordCommand;
import com.ead.authuser.user.specification.SpecificationTemplate;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

//    TODO: remover este service
    @Autowired
    private UserService queryService;

    @Autowired
    private UserAppService cmdService;

    public static final String USER_NOT_FOUND = "User not found.";
    public static final String USER_DELETED = "User deleted.";
    public static final String PASS_UPDATED = "Password updated.";

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(SpecificationTemplate.UserSpec spec,
                                                  @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                  @RequestParam(required = false) UUID courseId) {
        Page<User> userPage = null;

        if(courseId != null){
            userPage = queryService.findAll(SpecificationTemplate.userCourseId(courseId).and(spec), pageable);
        }else{
            userPage = queryService.findAll(spec, pageable);
        }

        if (!userPage.isEmpty()) {
            userPage.forEach(usr -> usr.add(linkTo(methodOn(UserController.class).getOneUSer(usr.getId())).withSelfRel()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneUSer(@PathVariable UUID id) {
        Optional<User> user = queryService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID id) {
        var cmd = DeleteUserCommand.of(id);

        cmdService.handle(cmd);

        return ResponseEntity.status(HttpStatus.OK).body(USER_DELETED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto) {

        var cmd = UpdateUserCommand.builder()
                .id(id)
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .cpf(dto.getCpf())
                .build();

        cmdService.handle(cmd);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "id") UUID id,
                                                 @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto dto) {
        var cmd = UpdateUserPasswordCommand.builder()
                .id(id)
                .oldPassword(dto.getOldPassword())
                .password(dto.getPassword())
                .build();

        cmdService.handle(cmd);

        return ResponseEntity.status(HttpStatus.OK).body(PASS_UPDATED);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto dto) {
        var cmd = UpdateUserImageCommand.of(id, dto.getImageUrl());

        cmdService.handle(cmd);

        return ResponseEntity.ok().build();
    }

}
