package com.ead.authuser.user.service.impl;

import com.ead.authuser.user.domain.model.User;
import com.ead.authuser.user.repository.UserRepository;
import com.ead.authuser.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public void delete(User userId) {
        repository.delete(userId);
    }

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Page<User> findAll(Specification<User> spec, Pageable pageable) {
        return repository.findAll(spec, pageable);
    }
}
