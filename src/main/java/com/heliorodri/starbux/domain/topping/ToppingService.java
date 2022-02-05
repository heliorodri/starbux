package com.heliorodri.starbux.domain.topping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingRepository repository;

    public Topping create(Topping topping) {
        return repository.save(topping);
    }

}
