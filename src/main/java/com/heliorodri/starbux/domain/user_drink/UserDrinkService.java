package com.heliorodri.starbux.domain.user_drink;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDrinkService {

    private final UserDrinkRepository repository;

    public UserDrink save(@NonNull UserDrink userDrink) {
        return repository.save(userDrink);
    }

}
