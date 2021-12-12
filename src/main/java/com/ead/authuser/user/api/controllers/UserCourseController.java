package com.ead.authuser.user.api.controllers;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.course.domain.model.UserCourse;
import com.ead.authuser.course.service.UserCourseService;
import com.ead.authuser.user.api.dtos.CourseDto;
import com.ead.authuser.user.api.dtos.UserCourseDto;
import com.ead.authuser.user.domain.model.User;
import com.ead.authuser.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserCourseController {

    @Autowired
    CourseClient courseClient;

    @Autowired
    private UserService queryService;

    @Autowired
    private UserCourseService userCourseService;

    public static final String USER_NOT_FOUND = "User not found.";

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseDto>> getAllCoursesByUser(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                               @PathVariable(value = "userId") UUID id){
        return ResponseEntity.ok().body(courseClient.getAllCoursesByUser(id, pageable));

    }

    @PostMapping("/users/{userId}/courses/subscription")
    public ResponseEntity<Object> saveSubscriptionUSerInCourse(@PathVariable(value = "userId") UUID id,
                                                               @RequestBody @Valid UserCourseDto dto){
        Optional<User> user = queryService.findById(id);

        if (!user.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(USER_NOT_FOUND);
        }

        if(userCourseService.existsByUserIdAndCourseId(id, dto.getCourseId())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Inscição já existe.");
        }

        UserCourse userCourse = userCourseService.save(user.get().convertToUserCourse(dto.getCourseId()));

        return ResponseEntity.status(HttpStatus.CREATED).body(userCourse);

    }
}
