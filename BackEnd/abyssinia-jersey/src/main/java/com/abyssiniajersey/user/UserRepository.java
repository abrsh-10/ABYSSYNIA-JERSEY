package com.abyssiniajersey.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    public Optional<User> findByUsername(String username);
    public Page<User> findByUserType(UserType userType, Pageable pageable);

    long countByUserType(UserType userType);
    List<User> findByFirstNameContainingIgnoreCaseAndUserType(String name,UserType userType);
    List<User> findByLastNameContainingIgnoreCaseAndUserType(String name,UserType userType);
    List<User> findByUsernameContainingIgnoreCaseAndUserType(String username,UserType userType);
}
