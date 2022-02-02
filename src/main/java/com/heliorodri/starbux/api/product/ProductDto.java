package com.heliorodri.starbux.api.product;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProductDto {

    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private Double price;


}
