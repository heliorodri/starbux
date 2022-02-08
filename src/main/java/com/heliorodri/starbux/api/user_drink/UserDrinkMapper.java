package com.heliorodri.starbux.api.user_drink;

import com.heliorodri.starbux.api.drink.DrinkMapper;
import com.heliorodri.starbux.api.topping.ToppingMapper;
import com.heliorodri.starbux.api.user.UserMapper;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDrinkMapper {

    private final DrinkMapper drinkMapper;
    private final ToppingMapper toppingMapper;
    private final UserMapper userMapper;

    public UserDrink toEntity(UserDrinkDto dto) {
        return UserDrink.builder()
                .id(dto.getId())
                .user(userMapper.toEntityRequest(dto.getUser()))
                .drink(drinkMapper.toEntity(dto.getDrink()))
                .toppings(dto.getToppings().stream().map(toppingMapper::toEntity).collect(Collectors.toList()))
                .build();
    }

    public UserDrinkDto toDto(UserDrink entity) {
        return UserDrinkDto.builder()
                .id(entity.getId())
                .user(userMapper.toDto(entity.getUser()))
                .drink(drinkMapper.toDto(entity.getDrink()))
                .toppings(entity.getToppings().stream().map(toppingMapper::toDto).collect(Collectors.toList()))
                .build();
    }
}
