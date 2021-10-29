package com.ead.authuser.controllers;

import com.ead.authuser.dtos.UserDto;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.services.UserService;
import com.ead.authuser.specifications.SpecificationTemplate;
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

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static java.util.Objects.nonNull;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public static final String USER_NOT_FOUND = "User not found.";
    public static final String USER_DELETED = "User deleted.";
    public static final String MISS_PASS = "Missmatch password!";
    public static final String PASS_UPDATED = "Password updated.";

    @GetMapping
    public ResponseEntity<Page<UserModel>> getAllUsers(SpecificationTemplate.UserSpec spec,
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.ASC)
                                                       Pageable pageable) {
        Page<UserModel> userModelPage = userService.findAll(spec, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getAllUsers(@PathVariable UUID userId) {
        Optional<UserModel> userModel = userService.findById(userId);

        if (!userModel.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModel);

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable UUID userId) {
        var userModel = userService.findById(userId).orElse(null);
        if (nonNull(userModel)) {
            userService.delete(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(USER_DELETED);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);

    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @RequestBody @Validated(UserDto.UserView.UserPut.class)
                                             @JsonView(UserDto.UserView.RegistrationPost.class) UserDto dto) {
        var userModel = userService.findById(userId).orElse(null);
        if (nonNull(userModel)) {
            userModel.update(dto);

            userService.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);

    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                                 @RequestBody @Validated(UserDto.UserView.PasswordPut.class)
                                                 @JsonView(UserDto.UserView.PasswordPut.class) UserDto dto) {
        var userModel = userService.findById(userId).orElse(null);
        boolean isPassEquals = nonNull(dto.getPassword()) && dto.getPassword().equals(dto.getOldPassword());

        if (userModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        } else if (isPassEquals) {
            userModel.setPassword(dto.getPassword());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(PASS_UPDATED);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MISS_PASS);


    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                              @RequestBody @Validated(UserDto.UserView.ImagePut.class)
                                              @JsonView(UserDto.UserView.ImagePut.class) UserDto dto) {
        var userModel = userService.findById(userId).orElse(null);

        if (nonNull(userModel)) {
            userModel.setImageUrl(dto.getImageUrl());
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            userService.save(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(MISS_PASS);


    }

}
