package com.heliorodri.starbux.domain.user_drink;

import com.heliorodri.starbux.domain.drink.Drink;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDrinkService {

    private final UserDrinkRepository repository;

    public List<UserDrink> findAllByProductId(Drink drink) {
        return repository.findAllByDrink(drink);
    }

}
