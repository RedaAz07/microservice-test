package com.lets_plat.mapper;

import org.springframework.stereotype.Component;

import com.lets_plat.dto.responsedto.AuthResponseDto;
import com.lets_plat.dto.responsedto.UserResponseDto;
import com.lets_plat.entity.User;

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
        return new UserResponseDto(user.getName(), user.getEmail(), user.getId());

    }

}
