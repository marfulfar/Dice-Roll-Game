package com.fargas.marcal.S5T2.services;


import com.fargas.marcal.S5T2.dtos.AuthenticationRequestDTO;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.dtos.RegisterRequestDTO;
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
public class AuthServiceImpl implements IAuthService{

    private final MongoTokenRepo tokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    //TODO both this class methods could be done with just strings as token
    public String register(RegisterRequestDTO request) {
        User user = User.builder()
                .name(request.getFirstName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        tokenRepo.save(user);

        //String jwtToken = jwtService.generateToken(user);
        return jwtService.generateToken(user);
    }


    public String authenticate(AuthenticationRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = tokenRepo
                .findByEmail(request.getEmail())
                .orElseThrow(()-> new NotFoundException("USER_NOT_FOUND", "user not found"));

        //String jwtToken = jwtService.generateToken(user);
        return jwtService.generateToken(user);
    }




}//closes class
