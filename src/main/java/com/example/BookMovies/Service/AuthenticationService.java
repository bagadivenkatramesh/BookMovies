package com.example.BookMovies.Service;

import com.example.BookMovies.DTO.LoginResponseDTO;
import com.example.BookMovies.DTO.LoginUserDTO;
import com.example.BookMovies.DTO.RegisterUserDTO;
import com.example.BookMovies.Entity.User;
import com.example.BookMovies.JWT.JwtService;
import com.example.BookMovies.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public User registerNormalUser(RegisterUserDTO registerUserDto){
        if(userRepository.findByUsername(registerUserDto.getUsername()).isPresent()){
            throw new RuntimeException("User with the username "+registerUserDto.getUsername()+" already exists");
        }
        User user = new User();
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(registerUserDto.getPassword());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public User registerAdminUser(RegisterUserDTO registerUserDto){
        if(userRepository.findByUsername(registerUserDto.getUsername()).isPresent()){
            throw new RuntimeException("Admin user with the username "+registerUserDto.getUsername()+" already exists");
        }
        User user = new User();
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER"); roles.add("ROLE_ADMIN");
        user.setUsername(registerUserDto.getUsername());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(registerUserDto.getPassword());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    public LoginResponseDTO login(LoginUserDTO loginUserDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDto.getUsername(),
                        loginUserDto.getPassword()
                )
        );
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return LoginResponseDTO.builder().
                jwtToken(token).build();
    }
}
