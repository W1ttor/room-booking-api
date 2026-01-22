package dev.abraao.roombookingapi.infra.auth.service;

import dev.abraao.roombookingapi.domain.user.Repository.UserRepository;
import dev.abraao.roombookingapi.domain.user.entity.User;
import dev.abraao.roombookingapi.infra.auth.dto.AuthResponse;
import dev.abraao.roombookingapi.infra.auth.dto.LoginRequest;
import dev.abraao.roombookingapi.infra.auth.dto.RegisterRequest;
import dev.abraao.roombookingapi.infra.security.JwtToken;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtToken jwtToken;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        String encryptedPassword = passwordEncoder.encode(registerRequest.getPassword());

        User user = User.create(registerRequest.getFirstName(), registerRequest.getLastName(), registerRequest.getEmail(), encryptedPassword);

        userRepository.save(user);

        var token = jwtToken.generateToken(user);

        return new AuthResponse(user.getId(), user.getFirstName(), user.getEmail(), token);
    }

    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(RuntimeException::new);

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        var token = jwtToken.generateToken(user);

        return new AuthResponse(user.getId(), user.getFirstName(), user.getEmail(), token);


    }

}
