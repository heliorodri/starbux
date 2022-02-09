package com.heliorodri.starbux.domain.topping;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingRepository repository;

    public Topping create(@NonNull Topping topping) {
        return repository.save(topping);
    }

    public void deleteById(@NonNull Long id) {
        repository.deleteById(id);
    }

    public List<Topping> findAll() {
        return repository.findAll();
    }

    public Topping findById(@NonNull Long id) {
        return repository.findById(id).orElseThrow();
    }

}
