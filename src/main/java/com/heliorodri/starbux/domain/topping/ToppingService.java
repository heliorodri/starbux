package com.heliorodri.starbux.domain.topping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ToppingService {

    private final ToppingRepository repository;

    public Topping create(Topping topping) {
        return repository.save(topping);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Topping> findAll() {
        return repository.findAll();
    }

}
