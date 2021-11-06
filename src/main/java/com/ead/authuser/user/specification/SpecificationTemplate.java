package com.ead.authuser.user.specification;

import com.ead.authuser.course.domain.model.UserCourse;
import com.ead.authuser.user.domain.model.User;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = "userType", spec = Equal.class),
            @Spec(path = "userStatus", spec = Equal.class),
            @Spec(path = "email", spec = Like.class),
            @Spec(path = "fullName", spec = Like.class)
    })
    public interface UserSpec extends Specification<User> {}

    public static Specification<User> userCourseId(final UUID courseId) {
        return (root, query, cb) -> {
            query.distinct(true);
            Join<User, UserCourse> userProd = root.join("userCourses");
            return cb.equal(userProd.get("courseId"), courseId);
        };
    }
}
