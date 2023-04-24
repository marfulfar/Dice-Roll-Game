package com.fargas.marcal.S5T2.repositories;

import com.fargas.marcal.S5T2.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SQLPlayerRepo extends JpaRepository<Player, Integer> {
}
