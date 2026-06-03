package com.example.Auth;

import com.example.User.*;
import com.example.Security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthService(PasswordEncoder passwordEncoder, UserRepository userRepository, UserService userService, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public ResponseEntity<String> register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.ROLE_USER);
        userRepository.save(user);
        return ResponseEntity.ok().body("User Registered Successfully.");
    }

    public ResponseEntity<Map<String, String>> login(User user) {
        UserDetails userDetails=userService.loadUserByUsername(user.getUsername());
        if(passwordEncoder.matches(user.getPassword(),userDetails.getPassword())){
            String accessToken=jwtUtil.generateAccessToken(user.getUsername());
            String refreshToken=jwtUtil.generateRefreshToken(user.getUsername());
            return ResponseEntity.ok().body(
                    Map.of(
                            "accessToken",accessToken,
                            "refreshToken",refreshToken
                    )
            );
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error","Invalid Credentials"));
    }
}
