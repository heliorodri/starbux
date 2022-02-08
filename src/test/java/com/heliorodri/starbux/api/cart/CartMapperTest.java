package com.heliorodri.starbux.api.cart;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.heliorodri.starbux.api.drink.DrinkDto;
import com.heliorodri.starbux.api.drink.DrinkMapper;
import com.heliorodri.starbux.api.topping.ToppingDto;
import com.heliorodri.starbux.api.topping.ToppingMapper;
import com.heliorodri.starbux.api.user.UserMapper;
import com.heliorodri.starbux.api.user_drink.UserDrinkMapper;
import com.heliorodri.starbux.domain.cart.Cart;
import com.heliorodri.starbux.domain.drink.Drink;
import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.topping.Topping;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CartMapperTest {

  private static CartMapper mapper;
  private static DrinkMapper drinkMapper;
  private static ToppingMapper toppingMapper;
  private static UserMapper userMapper;
  private static UserDrinkMapper userDrinkMapper;

  @BeforeAll
  public static void setUp() {
    drinkMapper = new DrinkMapper();
    toppingMapper = new ToppingMapper();
    userMapper = new UserMapper();
    userDrinkMapper = new UserDrinkMapper(drinkMapper, toppingMapper, userMapper);
    mapper = new CartMapper(drinkMapper, toppingMapper, userDrinkMapper);
  }

  @Test
  public void itShouldMapCartItemRequestToEntity() {
    final CartItemRequest request = buildRequest();
    final User user = buildUser();

    final Cart mappedCart = mapper.toEntity(request, user);

    assertNotNull(mappedCart);
    assertEquals(request.getId(), mappedCart.getId());
    assertEquals(request.getQuantity(), mappedCart.getQuantity());
    assertEquals(request.getDrink().getId(), mappedCart.getUserDrink().getDrink().getId());
    assertEquals(request.getDrink().getName(), mappedCart.getUserDrink().getDrink().getName());
    assertEquals(request.getDrink().getPrice(), mappedCart.getUserDrink().getDrink().getPrice());
    assertEquals(request.getToppings().get(0).getId(), mappedCart.getUserDrink().getToppings().get(0).getId());
    assertEquals(request.getToppings().get(0).getName(), mappedCart.getUserDrink().getToppings().get(0).getName());
    assertEquals(request.getToppings().get(0).getPrice(), mappedCart.getUserDrink().getToppings().get(0).getPrice());
  }

  @Test
  public void itShouldMapEntityToDto() {
    final Cart cart = buildCart();

    final CartItemDto dto = mapper.toDto(cart);

    assertNotNull(dto);
    assertEquals(cart.getId(), dto.getId());
    assertEquals(cart.getQuantity(), dto.getQuantity());
    assertEquals(cart.getUserDrink().getDrink().getId(), dto.getUserDrink().getDrink().getId());
    assertEquals(cart.getUserDrink().getDrink().getName(), dto.getUserDrink().getDrink().getName());
    assertEquals(cart.getUserDrink().getDrink().getPrice(), dto.getUserDrink().getDrink().getPrice());
    assertEquals(cart.getUserDrink().getToppings().get(0).getId(), dto.getUserDrink().getToppings().get(0).getId());
    assertEquals(cart.getUserDrink().getToppings().get(0).getName(), dto.getUserDrink().getToppings().get(0).getName());
    assertEquals(cart.getUserDrink().getToppings().get(0).getPrice(), dto.getUserDrink().getToppings().get(0).getPrice());
  }

  private Cart buildCart() {
    return Cart.builder()
        .id(1L)
        .user(buildUser())
        .userDrink(buildUserDrink())
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

  private CartItemRequest buildRequest() {
    return CartItemRequest.builder()
        .id(1L)
        .drink(buildDrinkDto())
        .toppings(List.of(buildToppingDto()))
        .quantity(5)
        .build();
  }

  private ToppingDto buildToppingDto() {
    return ToppingDto.builder()
        .id(1L)
        .name("topping")
        .price(1.5)
        .build();
  }

  private DrinkDto buildDrinkDto() {
    return DrinkDto.builder()
        .id(1L)
        .name("drink")
        .price(1.5)
        .build();
  }

}