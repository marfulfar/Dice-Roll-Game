package com.fargas.marcal.S5T2.repositories;

import com.fargas.marcal.S5T2.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SQLGameRepo extends JpaRepository<Game, Integer> {
}
