package com.fargas.marcal.S5T2.controllers;


import com.fargas.marcal.S5T2.dtos.AuthenticationRequestDTO;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.dtos.RegisterRequestDTO;
import com.fargas.marcal.S5T2.services.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final IAuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request){
        return ResponseEntity.ok(authService.register(request));
    }


    @PostMapping("/authenticate")
    public ResponseEntity<String> register(@RequestBody AuthenticationRequestDTO request){
        return ResponseEntity.ok(authService.authenticate(request));
    }



}
