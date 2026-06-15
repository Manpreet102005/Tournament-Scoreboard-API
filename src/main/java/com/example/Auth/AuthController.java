
package com.example.Auth;

import com.example.User.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@CrossOrigin(origins="*")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user){
        return authService.register(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String,String>> login(@RequestBody User user){
        return authService.login(user);
    }

    @PostMapping ("/refresh")
    ResponseEntity<Map<String,String>> refresh(@RequestBody Map<String,String> request){
        return authService.refresh(request);
    }
}
