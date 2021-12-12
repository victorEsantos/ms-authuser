package com.ead.authuser.user.domain.model;

import com.ead.authuser.course.domain.model.UserCourse;
import com.ead.authuser.user.domain.enums.UserStatus;
import com.ead.authuser.user.domain.enums.UserType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "TB_USERS")
public class User extends RepresentationModel<User> implements Serializable {
    public static final long serialVersionUID = -7444152881348052726L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true, length = 50)
    private String email;
    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String password;
    @Column(nullable = false, length = 150)
    private String fullName;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column(length = 20)
    private String phoneNumber;
    @Column(length = 20)
    private String cpf;
    @Column
    private String imageUrl;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime lastUpdateDate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserCourse> userCourses;

    public UserCourse convertToUserCourse(UUID courseId){
        return new UserCourse(null, this, courseId);
    }

    @Builder
    public User(UUID id, String username, String email, String password, String fullName, UserStatus userStatus,
                UserType userType, String phoneNumber, String cpf, String imageUrl, LocalDateTime creationDate,
                LocalDateTime lastUpdateDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.userStatus = userStatus;
        this.userType = userType;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
        this.imageUrl = imageUrl;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;

    }

    public User() {

    }

    public void update(String fullName, String phoneNumber, String cpf) {
        this.setFullName(fullName);
        this.setPhoneNumber(phoneNumber);
        this.setCpf(cpf);
        this.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    }
}
