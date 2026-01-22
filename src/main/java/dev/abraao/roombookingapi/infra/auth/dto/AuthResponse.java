package dev.abraao.roombookingapi.infra.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private UUID uuid;
    private String firstName;
    private String email;
    private String token;
}
