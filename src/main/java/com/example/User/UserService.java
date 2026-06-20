package com.example.User;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (User) userRepository.findById(username).orElseThrow(()->
                new UsernameNotFoundException("User Not Found: "+username));
    }

    public ResponseEntity<String> promoteToAdmin(String username) {
        User user = (User) userRepository.findById(username).orElseThrow(()->
                new UsernameNotFoundException("User Not Found: "+username));
        if (user.getUserRole() == UserRole.ROLE_ADMIN) {
            return org.springframework.http.ResponseEntity.badRequest().body("User is already an ADMIN.");
        }
        user.setUserRole(UserRole.ROLE_ADMIN);
        userRepository.save(user);
        return org.springframework.http.ResponseEntity.ok("User " + username + " has been promoted to ADMIN.");
    }

    public List<UserDTO> getAllUsers() {
       return userRepository.findByOrderByUserRoleAsc().stream().map(this::toUserDto).toList();
    }

    private UserDTO toUserDto(User user){
        return new UserDTO(user.getUsername(),user.getUserRole());
    }
}
