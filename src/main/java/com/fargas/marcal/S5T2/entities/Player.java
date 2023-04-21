package com.fargas.marcal.S5T2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "players")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {


    public Player(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @CreationTimestamp
    private LocalDateTime dateTime;
}
