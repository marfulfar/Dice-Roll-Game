package com.fargas.marcal.S5T2.repositories;

import com.fargas.marcal.S5T2.entities.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoGameRepo extends MongoRepository<Game, Integer> {
}
