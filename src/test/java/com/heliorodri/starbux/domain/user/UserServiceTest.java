package com.heliorodri.starbux.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.heliorodri.starbux.domain.authentication.Authentication;
import com.heliorodri.starbux.domain.authentication.AuthenticationRepository;
import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.exception.InvalidOperationException;
import com.heliorodri.starbux.domain.role.Role;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserServiceTest {

  public static final String USER_NAME = "user";
  public static final String USER_EMAIL = "user@mail.com";
  public static final String USER_PASSWORD = "pass";
  private static UserRepository repository;
  private static UserService service;
  private static AuthenticationService authService;
  private static AuthenticationRepository authRepository;

  @BeforeAll
  public static void setUp() {
    repository = mock(UserRepository.class);
    authRepository = mock(AuthenticationRepository.class);
    authService = new AuthenticationService(authRepository);
    service = new UserService(repository, authService);
  }

  @Test
  public void itShouldSignUp() throws NoSuchAlgorithmException {
    long userId = 12L;
    final User user = buildUser();

    when(repository.findByEmail(user.getEmail())).thenReturn(null);
    when(repository.save(any(User.class))).thenAnswer(invocation -> {
      User result = (User) invocation.getArguments()[0];
      result.setId(userId);
      return result;
    });

    when(authRepository.save(any(Authentication.class))).thenAnswer(invocation -> {
      Authentication authentication = (Authentication) invocation.getArguments()[0];
      authentication.setId(1);
      authentication.setToken(UUID.randomUUID().toString());

      return authentication;
    });

    User savedUser = service.signUp(user);

    assertEquals(savedUser.getId(), userId);
    assertEquals(savedUser.getName(), user.getName());
    assertEquals(savedUser.getEmail(), user.getEmail());
    assertEquals(savedUser.getRole(), user.getRole());
  }

  @Test
  public void itShouldThrowExceptionWhenTryingTOSignUpNullUser() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> service.signUp(null));

    assertEquals("userToSignUp is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenUserAlreadyExists() {
    final User user = buildUser();

    when(repository.findByEmail(user.getEmail())).thenReturn(user);

    InvalidOperationException exception =
        assertThrows(InvalidOperationException.class, () -> service.signUp(user));

    assertEquals("User already exists", exception.getMessage());
  }


  @Test
  public void itShouldSignIn() throws NoSuchAlgorithmException {
    final String expectedToken = UUID.randomUUID().toString();
    final User userToSignIn = buildUser();

    final User userFoundByEmail = buildUser();
    userFoundByEmail.setPassword(hashPassword(USER_PASSWORD));

    final Authentication authentication = Authentication.builder()
        .token(expectedToken)
        .user(userFoundByEmail)
        .build();

    when(repository.findByEmail(userFoundByEmail.getEmail())).thenReturn(userFoundByEmail);
    when(authRepository.findByUser(userFoundByEmail)).thenReturn(authentication);

    String token = service.signIn(userToSignIn);

    assertEquals(expectedToken, token);
  }

  @Test
  public void itShouldThrowExceptionWhenTryingToSignInUserThatNoExists() {
    final User user = buildUser();
    when(repository.findByEmail(any(String.class))).thenReturn(null);

    InvalidOperationException exception =
        assertThrows(InvalidOperationException.class, () -> service.signIn(user));

    assertEquals("user not found", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenTryingToSignInUserWithWrongPassword() {
    final User user = buildUser();
    when(repository.findByEmail(any(String.class))).thenReturn(user);

    InvalidOperationException exception =
        assertThrows(InvalidOperationException.class, () -> service.signIn(user));

    assertEquals("Wrong password", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenTryingToSignInUserAndTokenNotFound() throws NoSuchAlgorithmException {
    final User userToSignIn = buildUser();

    final User userFoundByEmail = buildUser();
    userFoundByEmail.setPassword(hashPassword(USER_PASSWORD));

    when(repository.findByEmail(any(String.class))).thenReturn(userFoundByEmail);
    when(authRepository.findByUser(userFoundByEmail)).thenReturn(null);

    InvalidOperationException exception =
        assertThrows(InvalidOperationException.class, () -> service.signIn(userToSignIn));

    assertEquals("token not found", exception.getMessage());
  }

  @Test
  public void itShouldThrowExceptionWhenTryingTOSignInNullUser() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> service.signIn(null));

    assertEquals("userToSignIn is marked non-null but is null", exception.getMessage());
  }

  private String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(password.getBytes());
    byte[] digest = md.digest();
    return DatatypeConverter.printHexBinary(digest).toUpperCase();
  }

  private User buildUser() {
    return User.builder()
        .name(USER_NAME)
        .email(USER_EMAIL)
        .password(USER_PASSWORD)
        .role(Role.ADMIN)
        .build();
  }

}