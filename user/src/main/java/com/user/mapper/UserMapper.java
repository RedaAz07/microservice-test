package com.user.mapper;

import org.springframework.stereotype.Component;

import com.user.dto.responsedto.AuthResponseDto;
import com.user.dto.responsedto.UserResponseDto;
import com.user.entity.User;

@Component
public class UserMapper {
    public AuthResponseDto toDto(String jwt) {
        if (jwt == null) {
            return null;

        }
        return new AuthResponseDto(jwt);
    }

    public UserResponseDto userToDto(User user) {
        if (user == null) {
            return null;
        }
        return new UserResponseDto(user.getName(), user.getEmail(), user.getId(), user.getRole());

    }

}
