package com.heliorodri.starbux.api.drink;

import com.heliorodri.starbux.domain.drink.Drink;
import org.springframework.stereotype.Component;

@Component
public class DrinkMapper {

    public Drink toEntity(DrinkDto dto) {
        return Drink.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    public Drink toEntity(DrinkCreateRequest request) {
        return Drink.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    public DrinkDto toDto(Drink drink) {
        return DrinkDto.builder()
                .id(drink.getId())
                .name(drink.getName())
                .price(drink.getPrice())
                .build();
    }

}
