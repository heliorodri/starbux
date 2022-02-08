package com.heliorodri.starbux.api.cart;


import com.heliorodri.starbux.api.drink.DrinkDto;
import com.heliorodri.starbux.api.topping.ToppingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequest {

    private Long id;
    private DrinkDto drink;
    private List<ToppingDto> toppings;
    private Integer quantity;

}
