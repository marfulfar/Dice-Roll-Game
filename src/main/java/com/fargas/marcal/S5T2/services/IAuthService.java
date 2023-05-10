package com.fargas.marcal.S5T2.services;

import com.fargas.marcal.S5T2.auth.AuthenticationRequest;
import com.fargas.marcal.S5T2.auth.AuthenticationResponse;
import com.fargas.marcal.S5T2.auth.RegisterRequest;

public interface IAuthService {

    public AuthenticationResponse register(RegisterRequest request);

    public AuthenticationResponse authenticate(AuthenticationRequest request);





}
