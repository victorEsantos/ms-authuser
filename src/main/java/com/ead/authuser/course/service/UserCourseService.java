package com.ead.authuser.course.service;

import com.ead.authuser.course.domain.model.UserCourse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserCourseService {
    boolean existsByUserIdAndCourseId(UUID id, UUID courseId);

    UserCourse save(UserCourse userCourse);
}
