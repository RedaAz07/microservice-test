package com.user.Service;

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

import com.user.dto.requestdto.EditUserRequestDto;
import com.user.dto.requestdto.LoginRequestDTO;
import com.user.dto.requestdto.RegisterRequestDTO;
import com.user.dto.responsedto.AuthResponseDto;
import com.user.dto.responsedto.UserResponseDto;
import com.user.entity.User;
import com.user.exceptions.ApiException;
import com.user.mapper.UserMapper;
import com.user.repository.UserRepository;
import com.user.security.CostumeUserDetails;
import com.user.security.JwtUtil;

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
     public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByName(username).orElseThrow(() -> ApiException.notFound("User Not Found"));
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
