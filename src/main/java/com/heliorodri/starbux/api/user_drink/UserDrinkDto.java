package com.heliorodri.starbux.api.user_drink;

import com.heliorodri.starbux.api.drink.DrinkDto;
import com.heliorodri.starbux.api.topping.ToppingDto;
import com.heliorodri.starbux.api.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDrinkDto {

    private Long id;
    private UserDto user;
    private DrinkDto drink;
    private List<ToppingDto> toppings;

}
