package com.heliorodri.starbux.api.drink;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
public class DrinkCreateRequest {

    @NotBlank
    private String name;

    @NotNull
    private Double price;

}
