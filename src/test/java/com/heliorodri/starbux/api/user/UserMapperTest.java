package com.heliorodri.starbux.api.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserMapperTest {

  public static final String USER_NAME = "test user";
  public static final String USER_EMAIL = "test@mail.com";
  public static final String USER_PASSWORD = "PASS";
  public static final long USER_ID = 1L;
  private static UserMapper mapper;

  @BeforeAll
  public static void setUp() {
    mapper = new UserMapper();
  }

  @Test
  public void itShouldMapDtoToEntity() {
    final UserDto dto = buildUserDto();

    final User mappedUser = mapper.toEntity(dto);

    assertNotNull(mappedUser);
    assertEquals(dto.getName(), mappedUser.getName());
    assertEquals(dto.getEmail(), mappedUser.getEmail());
    assertEquals(dto.getPassword(), mappedUser.getPassword());
    assertEquals(dto.getRole(), mappedUser.getRole());
  }

  @Test
  public void itShouldMapSignInRequestToEntity() {
    final UserSignInRequest request = buildRequest();

    final User mappedUser = mapper.toEntity(request);

    assertNotNull(mappedUser);
    assertEquals(request.getEmail(), mappedUser.getEmail());
    assertEquals(request.getPassword(), mappedUser.getPassword());
  }

  @Test
  public void itShouldMapUserListToResponseList() {
    final List<User> users = List.of(buildUser());

    final List<UserResponse> response = mapper.toResponseList(users);

    assertNotNull(response);
    assertEquals(users.get(0).getName(), response.get(0).getName());
    assertEquals(users.get(0).getEmail(), response.get(0).getEmail());
    assertEquals(users.get(0).getRole(), response.get(0).getRole());
  }

  @Test
  public void itShouldMapUserToResponse() {
    final User user = buildUser();

    final UserResponse response = mapper.toResponse(user);

    assertNotNull(response);
    assertEquals(user.getName(), response.getName());
    assertEquals(user.getEmail(), response.getEmail());
    assertEquals(user.getRole(), response.getRole());
  }

  @Test
  public void itShouldMapEntityToDto() {
    final User user = buildUser();

    final UserDto mappedDto = mapper.toDto(user);

    assertNotNull(mappedDto);
    assertEquals(user.getName(), mappedDto.getName());
    assertEquals(user.getEmail(), mappedDto.getEmail());
    assertEquals(user.getRole(), mappedDto.getRole());
    assertEquals(user.getPassword(), mappedDto.getPassword());
  }

  private User buildUser() {
    return User.builder()
        .id(USER_ID)
        .name(USER_NAME)
        .email(USER_EMAIL)
        .password(USER_PASSWORD)
        .role(Role.ADMIN)
        .build();
  }

  private UserSignInRequest buildRequest() {
    return UserSignInRequest.builder()
        .email(USER_EMAIL)
        .password(USER_PASSWORD)
        .build();
  }

  private UserDto buildUserDto() {
    return UserDto.builder()
        .name(USER_NAME)
        .email(USER_EMAIL)
        .password(USER_PASSWORD)
        .role(Role.ADMIN)
        .build();
  }

}