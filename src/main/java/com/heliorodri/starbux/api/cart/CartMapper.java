package com.heliorodri.starbux.api.cart;

import com.heliorodri.starbux.api.drink.DrinkDto;
import com.heliorodri.starbux.api.drink.DrinkMapper;
import com.heliorodri.starbux.api.topping.ToppingDto;
import com.heliorodri.starbux.api.topping.ToppingMapper;
import com.heliorodri.starbux.api.user_drink.UserDrinkMapper;
import com.heliorodri.starbux.domain.cart.Cart;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CartMapper {

    private final DrinkMapper drinkMapper;
    private final ToppingMapper toppingMapper;
    private final UserDrinkMapper userDrinkMapper;

    public Cart toEntity(CartItemRequest request, User user) {
        return Cart.builder()
                .id(request.getId())
                .user(user)
                .userDrink(buildUserDrink(request.getDrink(), request.getToppings(), user))
                .quantity(request.getQuantity())
                .build();
    }

    public CartItemDto toDto(Cart cart) {
        return CartItemDto.builder()
                .id(cart.getId())
                .userDrink(userDrinkMapper.toDto(cart.getUserDrink()))
                .quantity(cart.getQuantity())
                .build();
    }

    private UserDrink buildUserDrink(DrinkDto drink, List<ToppingDto> toppings, User user) {
        return UserDrink.builder()
                .user(user)
                .drink(drinkMapper.toEntity(drink))
                .toppings(toppingMapper.toEntityList(toppings))
                .build();
    }



}

