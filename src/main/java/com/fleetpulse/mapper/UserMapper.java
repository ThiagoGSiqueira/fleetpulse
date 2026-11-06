package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.User;
import com.fleetpulse.web.dto.UserRegisterRequestDTO;
import com.fleetpulse.web.dto.UserResponseDTO;

@Component 
public class UserMapper {
    public UserResponseDTO toDto(User userEntity) {
        return new UserResponseDTO(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getRole());
    }

    public User toEntity(UserRegisterRequestDTO userDto) {
        User user = User.builder()
        .name(userDto.name())
        .email(userDto.email())
        .password(userDto.password())
        .role(userDto.role())
        .build();

        return user;
    }
}
