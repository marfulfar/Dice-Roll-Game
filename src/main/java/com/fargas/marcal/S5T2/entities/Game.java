package com.fargas.marcal.S5T2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Document(collection="games")
public class Game {

    @MongoId
    private String id;
    @Column
    private String userID;
    @Column
    private byte dice1;
    @Column
    private byte dice2;
    @Column
    private boolean victory;
    @Column
    private String timeStamp;


    public Game(String userID, byte dice1, byte dice2, boolean victory, String timeStamp) {
        this.userID = userID;
        this.dice1 = dice1;
        this.dice2 = dice2;
        this.victory = victory;
        this.timeStamp = timeStamp;
    }
}
