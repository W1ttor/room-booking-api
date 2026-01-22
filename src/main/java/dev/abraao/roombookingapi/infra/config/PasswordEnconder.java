package dev.abraao.roombookingapi.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordEnconder {

    @Bean
    public PasswordEncoder PasswordEnconder() {
        return new BCryptPasswordEncoder();
    }
}
