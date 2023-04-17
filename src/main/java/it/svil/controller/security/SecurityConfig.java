package it.svil.controller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAnyRole;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(
                (authorize) -> {
                    try {
                        authorize
                                .requestMatchers("/students", "/courses", "/register").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/admin/**").access(hasRole("ADMIN"))
                                .requestMatchers("/students/add").access(hasAnyRole("ADMIN", "USER"))
                                .requestMatchers("/students/subscribe/**").access(hasRole("ADMIN"))
                                .requestMatchers("/students/delete/**").access(hasRole("ADMIN"))
                                .requestMatchers("/courses/delete/**").access(hasRole("ADMIN"))
                                .anyRequest().authenticated()
                                .and().csrf().disable().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/students", true);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            );
        return http.build();
    }
}