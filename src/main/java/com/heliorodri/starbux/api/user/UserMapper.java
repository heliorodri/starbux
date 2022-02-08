package com.heliorodri.starbux.api.user;

import com.heliorodri.starbux.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntity(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public User toEntity(UserSignInRequest dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }

}
