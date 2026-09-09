package com.example.BookMovies.Controller;

import com.example.BookMovies.DTO.RegisterUserDTO;
import com.example.BookMovies.Entity.User;
import com.example.BookMovies.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register_admin_user")
    public ResponseEntity<User> registerAdminUser(@RequestBody RegisterUserDTO registerUserDto){
        return ResponseEntity.ok(authenticationService.registerAdminUser(registerUserDto));
    }
}
