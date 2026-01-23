package dev.abraao.roombookingapi.infra.auth.service;

import dev.abraao.roombookingapi.domain.user.Repository.UserRepository;
import dev.abraao.roombookingapi.domain.user.entity.User;
import dev.abraao.roombookingapi.infra.auth.dto.AuthResponse;
import dev.abraao.roombookingapi.infra.auth.dto.LoginRequest;
import dev.abraao.roombookingapi.infra.auth.dto.RegisterRequest;
import dev.abraao.roombookingapi.infra.security.JwtToken;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtToken  jwtToken;

    @InjectMocks
    private AuthService authService;


    @Test
    void register() {

        RegisterRequest request = new RegisterRequest(
                "Wittor",
                "Abraão",
                "wittor@teste.com",
                "123456"
        );

        when(userRepository.existsByEmail(request.getEmail()))
                .thenReturn(false);

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encryptedPassword");

        when(jwtToken.generateToken(any(User.class)))
                .thenReturn("token");

        AuthResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("token", response.getToken());
        assertEquals("wittor@teste.com", response.getEmail());

        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder).encode(request.getPassword());
        verify(userRepository).save(any(User.class));
        verify(jwtToken).generateToken(any(User.class));
    }

    @Test
    void register_shouldThrowException_whenEmailAlreadyExists() {

        RegisterRequest request = new RegisterRequest(
                "Wittor",
                "Abraão",
                "wittor@teste.com",
                "123456"
        );

        when(userRepository.existsByEmail(request.getEmail()))
        .thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            authService.register(request);
        });

        verify(userRepository).existsByEmail(request.getEmail());
        verify(passwordEncoder, never()).encode(any());
        verify(userRepository, never()).save(any());
        verify(jwtToken, never()).generateToken(any());
    }

    @Test
    void Login() {
        LoginRequest request = new LoginRequest(
                "wittor@teste.com",
                "123456"
        );


        User user = User.create(
                "Wittor",
                "Teste",
                "wittor@teste.com",
                "encryptedPassword"
        );

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
                .thenReturn(true);

        when(jwtToken.generateToken(any(User.class)))
        .thenReturn("token");

        AuthResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("token", response.getToken());

        verify(userRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).matches(request.getPassword(), user.getPassword());
        verify(jwtToken).generateToken(any(User.class));
    }

    @Test
    void login_shouldThrowException_whenPasswordIsInvalid(){

        LoginRequest request = new LoginRequest(
                "wittor@teste.com",
                "123456"
        );

        User user = User.create(
                "Wittor",
                "Teste",
                "wittor@teste.com",
                "encryptedPassword"
        );

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(user));

        when(passwordEncoder.matches(request.getPassword(), user.getPassword()))
        .thenReturn(false);

        assertThrows(RuntimeException.class, () -> {
            authService.login(request);
        });

        verify(userRepository).findByEmail(request.getEmail());
        verify(passwordEncoder).matches(request.getPassword(), user.getPassword());
        verify(jwtToken, never()).generateToken(any(User.class));

    }

}