package com.heliorodri.starbux.api.cart;


import com.heliorodri.starbux.api.drink.DrinkDto;
import com.heliorodri.starbux.api.topping.ToppingDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartItemRequest {

    private Long id;
    private DrinkDto drink;
    private List<ToppingDto> toppings;
    private Integer quantity;

}
