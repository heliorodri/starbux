package com.heliorodri.starbux.domain.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.heliorodri.starbux.domain.exception.InvalidOperationException;
import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.user.User;
import java.util.UUID;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AuthenticationServiceTest {

  public static final long USER_ID = 1L;
  public static final String USER_EMAIL = "mail@mail.com";
  public static final String USER_NAME = "TEST";
  public static final String USER_PASSWORD = "pass";
  public static final int AUTH_ID = 1;
  public static final String AUTH_TOKEN = UUID.randomUUID().toString();

  private static AuthenticationService service;
  private static AuthenticationRepository repository;

  @BeforeAll
  private static void setUp(){
    repository = mock(AuthenticationRepository.class);
    service = new AuthenticationService(repository);
  }

  @Test
  public void itShouldSaveToken() {
    Authentication authentication = buildAuth();

    when(repository.save(authentication)).thenReturn(authentication);

    Authentication saved = service.saveToken(authentication);

    assertNotNull(saved);
    assertEquals(authentication.getToken(), saved.getToken());

    verify(repository, times(1)).save(authentication);
  }

  @Test
  public void itShouldThrowExceptionWhenSaveTokenWithNullAuthentication() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> service.saveToken(null));

    assertEquals("authentication is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void itShouldFindByUser() {
    final User user = buildUser();
    final Authentication authentication = buildAuth();

    when(repository.findByUser(user)).thenReturn(authentication);

    Authentication authFound = service.findByUser(user);

    assertEquals(authentication.getToken(), authFound.getToken());
    assertEquals(authentication.getId(), authFound.getId());
    assertEquals(authentication.getUser().getEmail(), authFound.getUser().getEmail());

    verify(repository, times(1)).findByUser(user);
  }

  @Test
  public void itShouldThrowExceptionTryingToFindByUserAndUserIsNull() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> service.findByUser(null));

    assertEquals("user is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void itShouldFindUserByToken() {
    final Authentication auth = buildAuth();

    when(repository.findByToken(auth.getToken())).thenReturn(auth);

    User userFound = service.findUserByToken(auth.getToken());

    assertEquals(USER_ID, userFound.getId());
    assertEquals(USER_NAME, userFound.getName());
    assertEquals(USER_EMAIL, userFound.getEmail());
  }

  @Test
  public void itShouldThrowExceptionTryingToFindByTokenAndTokenIsNull() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> service.findUserByToken(null));

    assertEquals("token is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenTokenIsEmpty() {
    InvalidOperationException exception =
        assertThrows(InvalidOperationException.class, () -> service.authenticate(""));

    assertEquals("Invalid Token. Authentication denied", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenUserNotFoundByToken() {
    String token = UUID.randomUUID().toString();

    when(repository.findByToken(token)).thenReturn(null);

    InvalidOperationException exception =
        assertThrows(InvalidOperationException.class, () -> service.authenticate(token));

    assertEquals("Authentication denied! User not found", exception.getMessage());
  }

  private Authentication buildAuth() {
    return Authentication.builder()
        .id(AUTH_ID)
        .token(AUTH_TOKEN)
        .user(buildUser())
        .build();
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

}