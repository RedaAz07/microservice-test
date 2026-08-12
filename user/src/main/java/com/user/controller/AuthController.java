package com.lets_plat.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lets_plat.Service.UserService;
import com.lets_plat.dto.requestdto.LoginRequestDTO;
import com.lets_plat.dto.requestdto.RegisterRequestDTO;
import com.lets_plat.dto.responsedto.AuthResponseDto;
import com.lets_plat.dto.responsedto.UserResponseDto;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody @Valid RegisterRequestDTO request) {
        AuthResponseDto response = userService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid LoginRequestDTO request) {
        AuthResponseDto response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    
}
