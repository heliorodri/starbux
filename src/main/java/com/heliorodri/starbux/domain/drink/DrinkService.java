package com.heliorodri.starbux.domain.drink;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository repository;

    public Drink save(@NotNull Drink drink) {
        return repository.save(drink);
    }

    public void delete(@NotNull Long id) {
        repository.deleteById(id);
    }

    public List<Drink> findAll() {
        return repository.findAll();
    }


}
