package com.heliorodri.starbux.api.topping;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Component
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToppingCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private Double price;

}
