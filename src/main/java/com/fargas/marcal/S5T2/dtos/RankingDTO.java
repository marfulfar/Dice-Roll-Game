package com.fargas.marcal.S5T2.dtos;


import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankingDTO {

    private String name;
    private float victoriesPerc;
}
