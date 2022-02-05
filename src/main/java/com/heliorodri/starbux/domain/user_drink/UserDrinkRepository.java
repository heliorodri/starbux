package com.heliorodri.starbux.domain.user_drink;

import com.heliorodri.starbux.domain.drink.Drink;
import com.heliorodri.starbux.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDrinkRepository extends JpaRepository<UserDrink, Long> {

    List<UserDrink> findAllByDrink(Drink drink);
    List<UserDrink> findAllByUser(User user);

}
