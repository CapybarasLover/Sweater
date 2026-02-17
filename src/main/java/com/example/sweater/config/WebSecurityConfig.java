package com.example.sweater.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.concurrent.ExecutionException;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) {
        // @formatter:off
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/", "/registration").permitAll() //прописываем пути, которые должны быть доступны без аутентификации
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
        return NoOpPasswordEncoder.getInstance(); //Настройка шифрования паролей
    }

//    @Bean
//    UserDetailsService userDetailsService(PasswordEncoder encoder) { //настраиваем хранилище данных
//        String password = encoder.encode("pass");
//        UserDetails user = User.withUsername("u").password(password).roles("USER").build();
//        return new InMemoryUserDetailsManager(user);
//    }

    // ГЛАВНОЕ: создаем UserDetailsService бин для JDBC authentication
    @Bean
    UserDetailsService userDetailsService() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // Настраиваем кастомные SQL запросы
        users.setUsersByUsernameQuery(
                // language=SQL
                "select username, password, active from usr where username=?"
        );
        users.setAuthoritiesByUsernameQuery(
                // language=SQL
                "select u.username, ur.roles from usr as u inner join user_role ur on u.id = ur.user_id where u.username=?"
        );

        return users;
    }
}