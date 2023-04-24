package com.fargas.marcal.S5T2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {

    private String user;
    private byte dice1;
    private byte dice2;
    private boolean victory;
    private LocalDateTime timeStamp;
}
