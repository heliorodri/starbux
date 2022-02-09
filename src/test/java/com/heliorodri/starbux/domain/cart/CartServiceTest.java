package com.heliorodri.starbux.domain.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.heliorodri.starbux.api.cart.CartMapper;
import com.heliorodri.starbux.api.cart.CompleteCartDto;
import com.heliorodri.starbux.api.drink.DrinkDto;
import com.heliorodri.starbux.api.drink.DrinkMapper;
import com.heliorodri.starbux.api.topping.ToppingDto;
import com.heliorodri.starbux.api.topping.ToppingMapper;
import com.heliorodri.starbux.api.user.UserMapper;
import com.heliorodri.starbux.api.user_drink.UserDrinkMapper;
import com.heliorodri.starbux.domain.drink.Drink;
import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.topping.Topping;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import com.heliorodri.starbux.domain.user_drink.UserDrinkRepository;
import com.heliorodri.starbux.domain.user_drink.UserDrinkService;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CartServiceTest {

  private static CartRepository repository;
  private static CartService service;
  private static CartMapper mapper;
  private static UserDrinkRepository userDrinkRepository;
  private static UserDrinkService userDrinkService;

  @BeforeAll
  public static void setUp() {
    DrinkMapper drinkMapper = new DrinkMapper();
    ToppingMapper toppingMapper = new ToppingMapper();
    UserMapper userMapper = new UserMapper();
    UserDrinkMapper userDrinkMapper = new UserDrinkMapper(drinkMapper, toppingMapper, userMapper);

    repository = mock(CartRepository.class);
    userDrinkRepository = mock(UserDrinkRepository.class);
    mapper = new CartMapper(drinkMapper, toppingMapper, userDrinkMapper);
    userDrinkService = new UserDrinkService(userDrinkRepository);

    service = new CartService(repository, mapper, userDrinkService);
  }

  @Test
  public void itShouldAddProduct() {
    int EXPECTED_TOTAL = 10;
    Cart cart = buildCart();

    when(userDrinkRepository.save(cart.getUserDrink())).thenReturn(cart.getUserDrink());
    when(repository.save(cart)).thenReturn(cart);
    when(repository.findByUserOrderByIdDesc(any(User.class))).thenReturn(List.of(cart));

    CompleteCartDto addedItem = service.addProduct(cart);

    assertNotNull(addedItem);

    DrinkDto addedDrink = addedItem.getCartItems().get(0).getUserDrink().getDrink();
    ToppingDto addedTopping = addedItem.getCartItems().get(0).getUserDrink().getToppings().get(0);

    assertEquals(cart.getUserDrink().getDrink().getName(), addedDrink.getName());
    assertEquals(cart.getUserDrink().getDrink().getPrice(), addedDrink.getPrice());
    assertEquals(cart.getUserDrink().getToppings().get(0).getName(), addedTopping.getName());
    assertEquals(cart.getUserDrink().getToppings().get(0).getPrice(), addedTopping.getPrice());
    assertEquals(EXPECTED_TOTAL, addedItem.getTotalCost());
  }

  @Test
  public void shouldThrowExceptionWhenTryingAddNullCart() {
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.addProduct(null));

    assertEquals("cart is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void itShouldUpdateItem() {
    int EXPECTED_TOTAL_BEFORE_UPDATE = 10;
    int EXPECTED_TOTAL_AFTER_UPDATE = 20;
    Cart cart = buildCart();
    User user = buildUser();

    when(userDrinkRepository.save(cart.getUserDrink())).thenReturn(cart.getUserDrink());
    when(repository.save(cart)).thenReturn(cart);
    when(repository.findByUserOrderByIdDesc(any(User.class))).thenReturn(List.of(cart));
    when(repository.getById(cart.getId())).thenReturn(cart);

    CompleteCartDto addedItem = service.addProduct(cart);

    assertNotNull(addedItem);
    assertEquals(EXPECTED_TOTAL_BEFORE_UPDATE, addedItem.getTotalCost());

    cart.setQuantity(4);
    CompleteCartDto updatedItem = service.updateItem(cart, user);

    assertNotNull(updatedItem);
    assertEquals(EXPECTED_TOTAL_AFTER_UPDATE, updatedItem.getTotalCost());
  }

  @Test
  public void shouldThrowExceptionWhenTryingUpdateWithNullCart() {
    User user = buildUser();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.updateItem(null, user));

    assertEquals("cart is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenTryingUpdateWithNullUser() {
    Cart cart = buildCart();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.updateItem(cart, null));

    assertEquals("user is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void shouldDeleteItem() {
    Cart cart = buildCart();
    User user = buildUser();

    when(userDrinkRepository.save(cart.getUserDrink())).thenReturn(cart.getUserDrink());
    when(repository.save(cart)).thenReturn(cart);
    when(repository.findByUserOrderByIdDesc(any(User.class))).thenReturn(List.of(cart));
    when(repository.existsById(cart.getId())).thenReturn(true);
    doNothing().when(repository).deleteById(cart.getId());

    CompleteCartDto addedItem = service.addProduct(cart);

    assertNotNull(addedItem);
    assertEquals(1, addedItem.getCartItems().size());

    when(repository.findByUserOrderByIdDesc(any(User.class))).thenReturn(List.of());
    CompleteCartDto deletedItem = service.deleteById(cart.getId(), user);
    assertEquals(0, deletedItem.getTotalCost());
  }

  @Test
  public void shouldThrowExceptionWhenTryingDeleteWithNullId() {
    User user = buildUser();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.deleteById(null, user));

    assertEquals("id is marked non-null but is null", exception.getMessage());
  }

  @Test
  public void shouldThrowExceptionWhenTryingDeleteWithNullUser() {
    Cart cart = buildCart();
    IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> service.deleteById(cart.getId(), null));

    assertEquals("user is marked non-null but is null", exception.getMessage());
  }

  private Cart buildCart() {
    return Cart.builder()
        .id(1L)
        .user(buildUser())
        .userDrink(buildUserDrink())
        .quantity(2)
        .build();
  }

  private UserDrink buildUserDrink() {
    return UserDrink.builder()
        .id(23L)
        .user(buildUser())
        .drink(buildDrink())
        .toppings(List.of(buildTopping()))
        .build();
  }

  private Topping buildTopping() {
    return Topping.builder()
        .id(99L)
        .name("topping")
        .price(2.5)
        .build();
  }

  private Drink buildDrink() {
    return Drink.builder()
        .id(20L)
        .name("drink")
        .price(2.5)
        .build();
  }

  private User buildUser() {
    return User.builder()
        .id(1L)
        .name("user")
        .email("user@mail.com")
        .password("pass")
        .role(Role.ADMIN)
        .build();
  }

}