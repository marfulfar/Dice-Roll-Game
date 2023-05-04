package com.fargas.marcal.S5T2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.ArrayList;
import java.util.List;


@Entity
@Document(collection = "players")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Player {


    public Player(String name) {
        this.name = name;
    }

    public Player(String name, String dateTime) {
        this.name = name;
        this.dateTime = dateTime;
        this.userGames = new ArrayList<>();
    }


    @MongoId
    private String id;

    //@Column
    @Column
    private String name;

    @Column
    private String dateTime;

    @Column
    private List<Game> userGames;
}
