package com.fargas.marcal.S5T2.services;

import com.fargas.marcal.S5T2.auth.AuthenticationRequest;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.auth.RegisterRequest;
import com.fargas.marcal.S5T2.config.JwtService;
import com.fargas.marcal.S5T2.entities.Role;
import com.fargas.marcal.S5T2.entities.User;
import com.fargas.marcal.S5T2.repositories.MongoTokenRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthServiceImplTest {

    @InjectMocks
    AuthServiceImpl authService;
    @Mock
    MongoTokenRepo tokenRepo;
    @Mock
    private JwtService jwtService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    private User user;

    private RegisterRequest regRequest;
    private UsernamePasswordAuthenticationToken userPassAuthToken;
    private AuthenticationRequest authRequest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("1","test","test@","test111",Role.USER);
        regRequest = new RegisterRequest("test","test@","test111");
        userPassAuthToken = new UsernamePasswordAuthenticationToken(regRequest.getEmail(), regRequest.getPassword());
        authRequest = new AuthenticationRequest(regRequest.getEmail(),regRequest.getPassword());

    }

    @Test
    void register() {
        //when
        when(jwtService.generateToken(any())).thenReturn("token");
        when(tokenRepo.save(any())).thenReturn(user);
        when(passwordEncoder.encode(any())).thenReturn(regRequest.getPassword());

        AuthenticationResponse token = (authService.register(regRequest));

        verify(tokenRepo).save(any());
        assertEquals("token",token.getToken());
    }

    @Test
    void authenticate() {
        //when
        when(jwtService.generateToken(any())).thenReturn("token");
        when(tokenRepo.findByEmail(any())).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any())).thenReturn(userPassAuthToken);

        AuthenticationResponse token = (authService.authenticate(authRequest));


        verify(tokenRepo).findByEmail(any());
        assertEquals("token",token.getToken());
    }
}