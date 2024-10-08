package com.crio.coderhack.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.crio.coderhack.models.User;

public interface UserRepository extends MongoRepository<User, String>{
    
}
