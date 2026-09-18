package com.fnb.oms_usermanagement.Service;

import com.fnb.oms_usermanagement.dto.LoginRequest;
import com.fnb.oms_usermanagement.dto.LoginResponse;
import com.fnb.oms_usermanagement.dto.RegisterRequest;
import com.fnb.oms_usermanagement.dto.RegisterResponse;

public interface AuthService {

    RegisterResponse register(RegisterRequest registerRequest);

    LoginResponse login(LoginRequest loginRequest);

}