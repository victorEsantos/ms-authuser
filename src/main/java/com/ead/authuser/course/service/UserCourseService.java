package com.ead.authuser.course.service;

import com.ead.authuser.course.repository.UserCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserCourseService {
    @Autowired
    UserCourseRepository repository;
}
