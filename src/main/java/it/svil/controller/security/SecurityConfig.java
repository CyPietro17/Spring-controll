package it.svil.controller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
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
                                .requestMatchers("/students", "/register").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/students/add").hasAnyRole("ADMIN", "USER")
                                .requestMatchers("/students/delete/**").hasRole("ADMIN")
                                .anyRequest().authenticated()
                                .and().formLogin().defaultSuccessUrl("/students")
                                .and().csrf(csrf -> csrf.disable());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            );
        return http.build();
    }

}
