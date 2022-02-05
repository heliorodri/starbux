package com.heliorodri.starbux.domain.drink;

import com.heliorodri.starbux.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

import static com.heliorodri.starbux.domain.role.Role.ADMIN;

@Service
@RequiredArgsConstructor
public class DrinkService {

    private final DrinkRepository repository;

    public Drink save(@NotNull Drink drink, @NotNull User user) {
        if (user.getRole() != ADMIN) {
            throw new RuntimeException("User must be ADMIN to create products");
        }

        return repository.save(drink);
    }

    public void delete(@NotNull Long id, @NotNull User user) {
        if (user.getRole() != ADMIN) {
            throw new RuntimeException("User must be ADMIN to delete products");
        }

        repository.deleteById(id);
    }

    public List<Drink> findAll() {
        return repository.findAll();
    }

    public Drink findById(@NotNull Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
    }

}
