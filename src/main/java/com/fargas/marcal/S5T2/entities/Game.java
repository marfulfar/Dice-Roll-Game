package com.fargas.marcal.S5T2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private int userID;
    @Column
    private byte dice1;
    @Column
    private byte dice2;
    @Column
    private boolean victory;
    @CreationTimestamp
    private LocalDateTime timeStamp;


    public Game(int userID, byte dice1, byte dice2, boolean victory) {
        this.userID = userID;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.victory = victory;
    }
}
