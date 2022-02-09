package com.heliorodri.starbux.domain.cart;

import com.heliorodri.starbux.api.cart.CartItemDto;
import com.heliorodri.starbux.api.cart.CompleteCartDto;
import com.heliorodri.starbux.api.cart.CartMapper;
import com.heliorodri.starbux.api.topping.ToppingDto;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrinkService;
import java.util.ArrayList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    public static final double DISCOUNT_PERCENTAGE = 0.25;
    private final CartRepository repository;
    private final CartMapper mapper;
    private final UserDrinkService userDrinkService;

    public CompleteCartDto addProduct(@NonNull Cart cart) {
        userDrinkService.save(cart.getUserDrink());
        repository.save(cart);
        log.info("order added to cart");

        return listItems(cart.getUser());
    }

    public CompleteCartDto updateItem(@NonNull Cart cart, @NonNull User user) {
        Cart itemToUpdate = repository.getById(cart.getId());
        itemToUpdate.setQuantity(cart.getQuantity());
        itemToUpdate.getUserDrink().setToppings(cart.getUserDrink().getToppings());
        itemToUpdate.getUserDrink().setDrink(cart.getUserDrink().getDrink());

        userDrinkService.save(cart.getUserDrink());
        repository.save(itemToUpdate);
        log.info("Cart with id {} updated", cart.getId());

        return listItems(user);
    }

    public CompleteCartDto deleteById(@NonNull Long id, @NonNull User user) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Item not found");
        }

        repository.deleteById(id);

        return listItems(user);
    }

    public CompleteCartDto listItems(User user) {
        List<CartItemDto> cartItemsDto = new ArrayList<>();
        double totalCost = 0;
        double totalAfterDiscount = 0;

        List<Cart> cartItems = repository.findByUserOrderByIdDesc(user);
        
        if (!cartItems.isEmpty()) {
            cartItemsDto = cartItems.stream().map(mapper::toDto).collect(Collectors.toList());
            totalCost = getTotalCost(cartItemsDto);
            totalAfterDiscount = getTotalAfterDiscount(cartItemsDto, totalCost);
        }

        return CompleteCartDto.builder()
                .cartItems(cartItemsDto.isEmpty() ? List.of() : cartItemsDto)
                .totalCost(totalCost)
                .totalAfterDiscount(totalAfterDiscount)
                .build();
    }

    private double getTotalCost(List<CartItemDto> cart) {
        return cart.stream()
                .mapToDouble(item -> {
                    var drinkPrice = item.getUserDrink().getDrink().getPrice();

                    var toppingsTotalPrice = item.getUserDrink().getToppings().stream()
                            .mapToDouble(ToppingDto::getPrice)
                            .sum();

                    var quantity = item.getQuantity();

                    return (drinkPrice + toppingsTotalPrice) * quantity;
                })
                .sum();
    }

    private double getTotalAfterDiscount(List<CartItemDto> cart, double totalCost) {
        var lowest_price = cart.stream().mapToDouble(item -> {
            var drink_price = item.getUserDrink().getDrink().getPrice();
            var toppings_total = item.getUserDrink().getToppings().stream().mapToDouble(ToppingDto::getPrice).sum();

            return drink_price + toppings_total;
        }).min().orElseThrow();

        var total_percent_discount = totalCost - (totalCost * DISCOUNT_PERCENTAGE);
        var total_lowest_price_discount = totalCost - lowest_price;

        if (totalCost > 12 && cart.size() < 3) {
            return total_percent_discount;
        }

        if (totalCost <= 12 && cart.size() >= 3) {
            return total_lowest_price_discount;
        }

        if (totalCost > 12 && cart.size() >= 3) {
            return Math.max(total_lowest_price_discount, total_percent_discount);
        }

        return totalCost;

    }

}


