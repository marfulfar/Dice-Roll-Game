package com.fargas.marcal.S5T2.services;

import com.fargas.marcal.S5T2.dtos.AuthenticationRequestDTO;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.dtos.RegisterRequestDTO;

public interface IAuthService {

    public String register(RegisterRequestDTO request);

    public String authenticate(AuthenticationRequestDTO request);





}
