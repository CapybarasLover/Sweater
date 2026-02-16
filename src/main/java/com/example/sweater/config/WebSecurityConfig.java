package com.example.sweater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        // @formatter:off
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/").permitAll() //прописываем пути, которые должны быть доступны без аутентификации
                        .anyRequest().authenticated() // остальные - требуем аутентификации
                )
                .formLogin((form) -> form
                        .loginPage("/login") //указываем страницу входа
                        .permitAll() //разрешаем заходить на нее всем
                )
                .logout(LogoutConfigurer::permitAll); // настраиваем логаут - доступен всем (по дефолту настроен на /logout)
        // @formatter:on

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //Настройка шифрования паролей
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder encoder) { //настраиваем хранилище данных
        String password = encoder.encode("pass");
        UserDetails user = User.withUsername("u").password(password).roles("USER").build();
        return new InMemoryUserDetailsManager(user);
    }
}