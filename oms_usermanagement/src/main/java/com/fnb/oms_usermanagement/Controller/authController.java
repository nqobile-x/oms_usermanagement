package com.fnb.oms_usermanagement.Controller;

import com.fnb.oms_usermanagement.dto.LoginRequest;
import com.fnb.oms_usermanagement.dto.LoginResponse;
import com.fnb.oms_usermanagement.dto.RegisterRequest;
import com.fnb.oms_usermanagement.dto.RegisterResponse;
import com.fnb.oms_usermanagement.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class authController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(registerRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

}