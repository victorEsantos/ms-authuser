package com.ead.authuser.services;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.specifications.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    Optional<UserModel> findById(UUID userId);

    void delete(UserModel userId);

    void save(UserModel userModel);

    boolean existsByUsername(String userName);

    boolean existsByEmail(String email);

    Page<UserModel> findAll(SpecificationTemplate.UserSpec spec, Pageable pageable);
}
