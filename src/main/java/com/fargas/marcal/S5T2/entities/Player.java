package com.fargas.marcal.S5T2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Date;


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
    }

    @MongoId
    private String id;

    @Column
    private String name;

    @Column
    private String dateTime;
}
