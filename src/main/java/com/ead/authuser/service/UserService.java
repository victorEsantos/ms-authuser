package com.ead.authuser.service;

import com.ead.authuser.domain.model.User;
import com.ead.authuser.specification.SpecificationTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAll();

    Optional<User> findById(UUID userId);

    void delete(User userId);

    void save(User user);

    boolean existsByUsername(String userName);

    boolean existsByEmail(String email);

    Page<User> findAll(SpecificationTemplate.UserSpec spec, Pageable pageable);
}
