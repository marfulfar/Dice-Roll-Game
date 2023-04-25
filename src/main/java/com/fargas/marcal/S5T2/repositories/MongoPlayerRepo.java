package com.fargas.marcal.S5T2.repositories;

import com.fargas.marcal.S5T2.entities.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPlayerRepo extends MongoRepository<Player, Integer> {
}
