package com.ead.authuser.course.service.impl;

import com.ead.authuser.course.domain.model.UserCourse;
import com.ead.authuser.course.repository.UserCourseRepository;
import com.ead.authuser.course.service.UserCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class UserCourseServiceImpl implements UserCourseService {
    @Autowired
    UserCourseRepository repository;

    public boolean existsByUserIdAndCourseId(UUID id, UUID courseId) {
        return repository.existsByUserIdAndCourseId(id, courseId);
    }

    @Override
    public UserCourse save(UserCourse userCourse) {
        return repository.save(userCourse);
    }

    @Override
    public boolean existsByCourseId(UUID courseId) {
        return repository.existsByCourseId(courseId);
    }

    @Transactional
    @Override
    public void deleteUserCourseByCourse(UUID courseId) {
        repository.deleteAllByCourseId(courseId);
    }
}
