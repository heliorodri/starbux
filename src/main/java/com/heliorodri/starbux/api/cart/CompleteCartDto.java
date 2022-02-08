package com.heliorodri.starbux.api.cart;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class CompleteCartDto {

    private List<CartItemDto> cartItems;
    private double totalCost;
    private double totalAfterDiscount;

}
