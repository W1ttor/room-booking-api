package dev.abraao.roombookingapi.infra.auth.dto;

import dev.abraao.roombookingapi.domain.user.entity.User;
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

    public static AuthResponse fromAuthResponse(User user, String token) {
        return new AuthResponse(user.getId(), user.getFirstName(), user.getEmail(), token);
    }
}
