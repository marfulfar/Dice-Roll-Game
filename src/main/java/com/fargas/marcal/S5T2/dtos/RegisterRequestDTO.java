package com.fargas.marcal.S5T2.dtos;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class RegisterRequestDTO {

    private String firstName;
    private String email;
    private String password;
}
