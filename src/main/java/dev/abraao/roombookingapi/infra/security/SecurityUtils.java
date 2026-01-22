package dev.abraao.roombookingapi.infra.security;

import dev.abraao.roombookingapi.domain.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    @Bean
    public User getUserDetailsService() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) throw new IllegalStateException("No authentication provided");

        Object principal = authentication.getPrincipal();

        if (principal instanceof User user) {
            return user;
        }

        throw new RuntimeException("Authentication object is not an instance of User");
    }
}
