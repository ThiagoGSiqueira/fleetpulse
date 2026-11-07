package com.fleetpulse.mapper;

import org.springframework.stereotype.Component;

import com.fleetpulse.domain.User;
import com.fleetpulse.web.dto.UserRegisterRequestDTO;
import com.fleetpulse.web.dto.UserResponseLoginDTO;
import com.fleetpulse.web.dto.UserResponseRegisterDTO;

@Component 
public class UserMapper {
    public UserResponseRegisterDTO toRegisterDto(User userEntity) {
        return new UserResponseRegisterDTO(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), userEntity.getRole());
    }

    public UserResponseLoginDTO toLoginDto(User userEntity, String token) {
        return new UserResponseLoginDTO(userEntity.getId(), userEntity.getName(), userEntity.getEmail(), token);
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
