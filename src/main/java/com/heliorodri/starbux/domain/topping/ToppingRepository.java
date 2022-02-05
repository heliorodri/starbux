package com.heliorodri.starbux.domain.topping;

import com.heliorodri.starbux.domain.topping.Topping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToppingRepository extends JpaRepository<Topping, Long> {
}
