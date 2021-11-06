package com.ead.authuser.course.repository;

import com.ead.authuser.course.domain.model.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface UserCourseRepository extends JpaRepository<UserCourse, UUID> {

}
