package com.heliorodri.starbux.api.cart;

import com.heliorodri.starbux.api.user_drink.UserDrinkDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@Builder
public class CartItemDto {

    private Long id;

    @NotNull
    private  Integer quantity;

    @NotNull
    private UserDrinkDto userDrink;

}
