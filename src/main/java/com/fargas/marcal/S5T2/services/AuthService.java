package com.fargas.marcal.S5T2.services;


import com.fargas.marcal.S5T2.auth.AuthenticationRequest;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.auth.RegisterRequest;
import com.fargas.marcal.S5T2.config.JwtService;
import com.fargas.marcal.S5T2.entities.Role;
import com.fargas.marcal.S5T2.entities.User;
import com.fargas.marcal.S5T2.exceptions.NotFoundException;
import com.fargas.marcal.S5T2.repositories.MongoTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MongoTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    //TODO both this class methods could be done with just strings as token
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .name(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        tokenRepo.save(user);

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = tokenRepo
                .findByEmail(request.getEmail())
                .orElseThrow(()-> new NotFoundException("USER_NOT_FOUND", "user not found")); //TODO handle exception

        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }




}//closes class
