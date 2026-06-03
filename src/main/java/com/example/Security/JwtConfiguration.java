package com.example.Security;

import com.example.User.UserRole;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtConfiguration {
    public SecurityFilterChain filter(HttpSecurity request, JwtFilter jwtFilter) throws Exception {
        return request
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.
                                requestMatchers(
                                        "/user/login",
                                        "/user/register",
                                        "/user/refresh"
                                ).permitAll()
                                .requestMatchers("/admin/**").hasAuthority(UserRole.ROLE_ADMIN.name())
                                .requestMatchers("/user/**").hasAuthority(UserRole.ROLE_USER.name())
                                .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
