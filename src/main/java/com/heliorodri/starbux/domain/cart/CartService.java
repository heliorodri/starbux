package com.heliorodri.starbux.domain.cart;

import com.heliorodri.starbux.api.cart.CartItemDto;
import com.heliorodri.starbux.api.cart.CartItemsDto;
import com.heliorodri.starbux.api.cart.CartMapper;
import com.heliorodri.starbux.api.topping.ToppingDto;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository repository;
    private final CartMapper mapper;
    private final UserDrinkService userDrinkService;

    public CartItemsDto addProduct(Cart cart) {
        userDrinkService.save(cart.getUserDrink());
        repository.save(cart);
        log.info("order added to cart");

        return listItems(cart.getUser());
    }

    public CartItemsDto updateItem(Cart cart, User user) {
        Cart itemToUpdate = repository.getById(cart.getId());
        itemToUpdate.setQuantity(cart.getQuantity());

        userDrinkService.save(cart.getUserDrink());
        repository.save(itemToUpdate);
        log.info("Cart with id {} updated", cart.getId());

        return listItems(user);
    }

    public CartItemsDto deleteItem(Long itemId, User user) {
        if (!repository.existsById(itemId)) {
            throw new RuntimeException("Item not found");
        }

        repository.deleteById(itemId);

        return listItems(user);
    }

    public CartItemsDto listItems(User user) {
        List<Cart> cartItems = repository.findByUserOrderByIdDesc(user);
        List<CartItemDto> cartItemsDto = cartItems.stream().map(mapper::toDto).collect(Collectors.toList());

        double totalCost = getTotalCost(cartItemsDto);
        double totalAfterDiscount = getTotalAfterDiscount(cartItemsDto, totalCost);

        return CartItemsDto.builder()
                .cartItems(cartItemsDto)
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

        var total_percent_discount = totalCost - (totalCost * (25/100));
        var total_lowest_price_discount = totalCost - lowest_price;

        if (totalCost > 12 && cart.size() < 3) {
            return total_percent_discount;
        }

        if (totalCost < 12 && cart.size() >= 3) {
            return total_lowest_price_discount;
        }

        return Math.min(total_lowest_price_discount, total_percent_discount);

    }

}
