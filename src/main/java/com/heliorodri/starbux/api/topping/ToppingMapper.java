package com.heliorodri.starbux.api.topping;

import com.heliorodri.starbux.domain.topping.Topping;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ToppingMapper {

    public List<Topping> toEntityList(List<ToppingDto> dtos) {
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public List<ToppingDto> toDtoList(List<Topping> entities) {
        return entities.stream().map(this::toDto).collect(Collectors.toList());
    }

    public Topping toEntity(ToppingCreateRequest request) {
        return Topping.builder()
                .name(request.getName())
                .price(request.getPrice())
                .build();
    }

    public Topping toEntity(ToppingDto dto) {
        return Topping.builder()
                .id(dto.getId())
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    public ToppingDto toDto(Topping topping) {
        return ToppingDto.builder()
                .id(topping.getId())
                .name(topping.getName())
                .price(topping.getPrice())
                .build();
    }

}
