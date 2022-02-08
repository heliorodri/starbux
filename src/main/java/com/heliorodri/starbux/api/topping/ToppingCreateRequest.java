package com.heliorodri.starbux.api.topping;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
public class ToppingCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private Double price;

}
