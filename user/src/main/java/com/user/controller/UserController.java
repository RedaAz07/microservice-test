package com.lets_plat.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lets_plat.Service.UserService;
import com.lets_plat.dto.requestdto.EditUserRequestDto;
import com.lets_plat.dto.requestdto.ProductRequestDto;
import com.lets_plat.dto.requestdto.RegisterRequestDTO;
import com.lets_plat.dto.responsedto.ProductResponseDto;
import com.lets_plat.dto.responsedto.UserResponseDto;
import com.lets_plat.utils.RateLimited;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;
@RateLimited
    @GetMapping()
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getuser(@PathVariable String id) {
        UserResponseDto user = userService.getUser(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> editUser(@RequestBody @Valid EditUserRequestDto request,
            @PathVariable String id) {
        UserResponseDto user = userService.editUser(request, id);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable String id) {
        String res = userService.deleteUser(id);
        return ResponseEntity.ok(res);
    }
}
