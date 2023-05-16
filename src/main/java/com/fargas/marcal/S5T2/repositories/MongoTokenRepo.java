package com.fargas.marcal.S5T2.repositories;

import com.fargas.marcal.S5T2.entities.Player;
import com.fargas.marcal.S5T2.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface MongoTokenRepo extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);

}
