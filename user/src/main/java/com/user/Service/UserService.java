package com.lets_plat.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lets_plat.dto.requestdto.EditUserRequestDto;
import com.lets_plat.dto.requestdto.LoginRequestDTO;
import com.lets_plat.dto.requestdto.ProductRequestDto;
import com.lets_plat.dto.requestdto.RegisterRequestDTO;
import com.lets_plat.dto.responsedto.AuthResponseDto;
import com.lets_plat.dto.responsedto.ProductResponseDto;
import com.lets_plat.dto.responsedto.UserResponseDto;
import com.lets_plat.entity.Product;
import com.lets_plat.entity.User;
import com.lets_plat.exceptions.ApiException;
import com.lets_plat.mapper.UserMapper;
import com.lets_plat.repository.UserRepository;
import com.lets_plat.security.CostumeUserDetails;
import com.lets_plat.security.JwtUtil;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CostumeUserDetails userDetailsService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDto register(RegisterRequestDTO request) {

        if (userRepository.existsByEmail(request.email())) {
            throw ApiException.conflict("Email already Exists");
        }
        if (userRepository.existsByName(request.name())) {
            throw ApiException.conflict("Email already Exists");
        }
        User newUser = new User();
        newUser.setEmail(request.email());
        newUser.setName(request.name());
        newUser.setRole("USER");
        newUser.setPassword(passwordEncoder.encode(request.password()));
        userRepository.save(newUser);

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.name());
        final String jwt = jwtUtil.generateToken(userDetails);
        return userMapper.toDto(jwt);
    }

    public AuthResponseDto login(LoginRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.name(), request.password()));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.name());
        final String jwt = jwtUtil.generateToken(userDetails);
        return userMapper.toDto(jwt);
    }

    public List<UserResponseDto> getUsers() {
        List<UserResponseDto> users = userRepository.findAll().stream().map(s -> userMapper.userToDto(s))
                .collect(Collectors.toList());

        return users;
    }

    public UserResponseDto getUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> ApiException.notFound("User Not Found"));
        return userMapper.userToDto(user);
    }

    public UserResponseDto editUser(EditUserRequestDto request, String id) {
        User user = userRepository.findById(id).orElseThrow(() -> ApiException.notFound("User Not Found"));
        user.setRole(request.Role());
        User savedP = userRepository.save(user);
        return userMapper.userToDto(savedP);

    }

    public String deleteUser(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> ApiException.notFound("User Not Found"));
        if (user.getName().equals("Admin")) {
            throw ApiException.forbidden("Access denied.");

        }
        userRepository.delete(user);
        return "Seccusefully ";
    }

}
