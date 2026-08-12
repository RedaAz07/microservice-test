package com.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.user.entity.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByName(String username);

    boolean existsByName(String username);

    boolean existsByEmail(String email);

}
