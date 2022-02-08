package com.heliorodri.starbux.domain.user_drink;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDrinkRepository extends JpaRepository<UserDrink, Long> {
}
