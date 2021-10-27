package com.ead.authuser.services.impl;

import com.ead.authuser.models.UserModel;
import com.ead.authuser.repositories.UserRepository;
import com.ead.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository repository;

    @Override
    public List<UserModel> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<UserModel> findById(UUID userId) {
        return repository.findById(userId);
    }

    @Override
    public void delete(UserModel userId) {
        repository.delete(userId);
    }

    @Override
    public void save(UserModel userModel) {
        repository.save(userModel);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
