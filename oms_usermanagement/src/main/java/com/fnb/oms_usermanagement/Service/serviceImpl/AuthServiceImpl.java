package com.fnb.oms_usermanagement.Service.serviceImpl;

import com.fnb.oms_usermanagement.dto.LoginRequest;
import com.fnb.oms_usermanagement.dto.LoginResponse;
import com.fnb.oms_usermanagement.dto.RegisterRequest;
import com.fnb.oms_usermanagement.dto.RegisterResponse;
import com.fnb.oms_usermanagement.entity.Role;
import com.fnb.oms_usermanagement.entity.User;
import com.fnb.oms_usermanagement.entity.UserCredential;
import com.fnb.oms_usermanagement.repository.UserCredentialsRepository;
import com.fnb.oms_usermanagement.repository.UserRepository;
import com.fnb.oms_usermanagement.security.JwtService;
import com.fnb.oms_usermanagement.Service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserCredentialsRepository userCredentialsRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(UserRepository userRepository,
                           UserCredentialsRepository userCredentialsRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService) {
        this.userRepository = userRepository;
        this.userCredentialsRepository = userCredentialsRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        User user = User.builder()
                .firstName(registerRequest.getFirstName())
                .surname(registerRequest.getSurname())
                .email(registerRequest.getEmail())
                .role(Role.CUSTOMER)
                .build();
        user = userRepository.save(user);

        UserCredential userCredential = UserCredential.builder()
                .user(user)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        userCredentialsRepository.save(userCredential);

        return toUserResponse(user);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        UserCredential credential = userCredentialsRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), credential.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        return new LoginResponse(token);
    }

    private RegisterResponse toUserResponse(User user) {
        return RegisterResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}