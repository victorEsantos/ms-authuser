package com.ead.authuser.service;

import com.ead.authuser.domain.enums.UserStatus;
import com.ead.authuser.domain.enums.UserType;
import com.ead.authuser.domain.model.User;
import com.ead.authuser.repository.UserRepository;
import com.ead.authuser.service.command.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import static java.util.Objects.nonNull;

@Service
@Transactional
@AllArgsConstructor
public class UserAppService {
    @Autowired
    UserRepository repository;

    public User handle(final RegisterUserCommand cmd) {
        User user = User
                .builder()
                .id(UUID.randomUUID())
                .username(cmd.getUsername())
                .email(cmd.getEmail())
                .password(cmd.getPassword())
                .fullName(cmd.getFullName())
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.STUDANT)
                .phoneNumber(cmd.getPhoneNumber())
                .cpf(cmd.getCpf())
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        repository.save(user);

        return user;
    }

    public void handle(final UpdateUserPasswordCommand cmd) {
//      Todo implementar @ExceptionHandler
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
        boolean isPassEquals = nonNull(cmd.getOldPassword()) && cmd.getOldPassword().equals(user.getPassword());

        if (isPassEquals) {
            user.setPassword(cmd.getPassword());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            repository.save(user);
        } else {
            throw new RuntimeException("Missmatch password");
        }
    }

    public void handle(UpdateUserImageCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));

        if (nonNull(user)) {
            user.setImageUrl(cmd.getImageUrl());
            user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

            repository.save(user);
        }
    }

    public void handle(UpdateUserCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
        if (nonNull(user)) {
            user.update(cmd.getFullName(), cmd.getPhoneNumber(), cmd.getCpf());

            repository.save(user);
        }
    }

    public void handle(DeleteUserCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new RuntimeException("USER NOT FOUND"));
        repository.delete(user);
    }
}
