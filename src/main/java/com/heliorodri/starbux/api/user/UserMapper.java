package com.heliorodri.starbux.api.user;

import com.heliorodri.starbux.domain.user.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public User toEntityRequest(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(dto.getRole())
                .build();
    }

    public User toEntityRequest(UserSignInRequest dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .build();
    }

    public List<UserResponse> toResponseList(List<User> users) {
        return users.stream().map(this::toReponse).collect(Collectors.toList());
    }

    public UserResponse toReponse(User user) {
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
