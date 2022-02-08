package com.heliorodri.starbux.domain.user_drink;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.heliorodri.starbux.domain.drink.Drink;
import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.topping.Topping;
import com.heliorodri.starbux.domain.user.User;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserDrinkServiceTest {

  private static UserDrinkRepository repository;
  private static UserDrinkService service;

  @BeforeAll
  public static void setUp() {
    repository = mock(UserDrinkRepository.class);
    service = new UserDrinkService(repository);
  }

  @Test
  public void itShouldSaveUserDrink() {
    final UserDrink userDrink = buildUserDrink();

    when(repository.save(userDrink)).thenReturn(userDrink);

    UserDrink saved = service.save(userDrink);

    assertEquals(userDrink.getId(), saved.getId());
    assertEquals(userDrink.getUser().getEmail(), saved.getUser().getEmail());
    assertEquals(userDrink.getDrink().getName(), saved.getDrink().getName());
    assertEquals(userDrink.getToppings().get(0).getName(), saved.getToppings().get(0).getName());

    verify(repository).save(userDrink);
  }

  @Test
  public void itShouldThrowExceptionWhenParamIsNull() {
    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> service.save(null));

    assertEquals("userDrink is marked non-null but is null", exception.getMessage());
  }

  private UserDrink buildUserDrink() {
    return UserDrink.builder()
        .id(1L)
        .user(buildUser())
        .drink(buildDrink())
        .toppings(List.of(buildTopping()))
        .build();
  }

  private Topping buildTopping() {
    return Topping.builder()
        .id(1L)
        .name("topping")
        .price(1.5)
        .build();
  }

  private Drink buildDrink() {
    return Drink.builder()
        .id(1L)
        .name("drink")
        .price(10.5)
        .build();
  }

  private User buildUser() {
    return User.builder()
        .id(1L)
        .name("test")
        .email("test@mail.com")
        .password("pass")
        .role(Role.ADMIN)
        .build();
  }

}