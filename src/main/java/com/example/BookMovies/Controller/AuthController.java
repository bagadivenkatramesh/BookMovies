package com.example.BookMovies.Controller;

import com.example.BookMovies.DTO.LoginResponseDTO;
import com.example.BookMovies.DTO.LoginUserDTO;
import com.example.BookMovies.DTO.RegisterUserDTO;
import com.example.BookMovies.Entity.User;
import com.example.BookMovies.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.BookMovies.DTO.UserResponseDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register_normal_user")
    public ResponseEntity<User> registerNormalUser(@RequestBody RegisterUserDTO registerUserDto){
        return ResponseEntity.ok(authenticationService.registerNormalUser(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginUserDTO loginUserDto){
        return ResponseEntity.ok(authenticationService.login(loginUserDto));
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDTO> getCurrentUser(
            @AuthenticationPrincipal User user) {

        UserResponseDTO userResponseDto = UserResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getRoles())
                .build();

        return ResponseEntity.ok(userResponseDto);
    }
}
