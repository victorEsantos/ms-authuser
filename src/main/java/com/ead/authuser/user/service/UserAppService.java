package com.ead.authuser.user.service;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.course.domain.model.UserCourse;
import com.ead.authuser.course.repository.UserCourseRepository;
import com.ead.authuser.user.domain.enums.UserStatus;
import com.ead.authuser.user.domain.enums.UserType;
import com.ead.authuser.user.domain.model.User;
import com.ead.authuser.user.repository.UserRepository;
import com.ead.authuser.user.service.command.*;
import com.ead.authuser.user.service.exception.ObjectNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static java.util.Objects.nonNull;

@Service
@Transactional
@AllArgsConstructor
public class UserAppService {
    @Autowired
    UserRepository repository;

    @Autowired
    UserCourseRepository userCourseRepository;

    @Autowired
    CourseClient courseClient;

    public User handle(final RegisterUserCommand cmd) {
        User user = User
                .builder()
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

        return repository.save(user);
    }

    public void handle(final UpdateUserPasswordCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new ObjectNotFoundException("USER NOT FOUND"));
        boolean isPassEquals = nonNull(cmd.getOldPassword()) && cmd.getOldPassword().equals(user.getPassword());

        if (isPassEquals) {
            user.setPassword(cmd.getPassword());

            repository.save(user);
        } else {
            throw new RuntimeException("Missmatch password");
        }
    }

    public void handle(UpdateUserImageCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new ObjectNotFoundException("USER NOT FOUND"));

        if (nonNull(user)) {
            user.setImageUrl(cmd.getImageUrl());

            repository.save(user);
        }
    }

    public void handle(UpdateUserCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new ObjectNotFoundException("USER NOT FOUND"));
        if (nonNull(user)) {
            user.update(cmd.getFullName(), cmd.getPhoneNumber(), cmd.getCpf());

            repository.save(user);
        }
    }

    public void handle(DeleteUserCommand cmd) {
        var user = repository.findById(cmd.getId()).orElseThrow(() -> new ObjectNotFoundException("USER NOT FOUND"));
        var deleteUserCourseInCourse = false;

        List<UserCourse> userCourseList = userCourseRepository.findAllUserCourseIntoUser(user.getId());

        if(!userCourseList.isEmpty()){
            userCourseRepository.deleteAll(userCourseList);
            deleteUserCourseInCourse = true;
        }

        if(deleteUserCourseInCourse){
            courseClient.deleteUserInCourse(user.getId());
        }

        repository.delete(user);
    }
}
