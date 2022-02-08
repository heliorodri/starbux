package com.heliorodri.starbux.domain.drink;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository repository;

    public Drink save(@NonNull Drink drink) {
        return repository.save(drink);
    }

    public void delete(@NonNull Long id) {
        repository.deleteById(id);
    }

    public List<Drink> findAll() {
        return repository.findAll();
    }


}
