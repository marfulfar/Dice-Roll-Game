package com.fargas.marcal.S5T2.auth;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class RegisterRequest {

    private String firstName;
    private String email;
    private String password;
}
