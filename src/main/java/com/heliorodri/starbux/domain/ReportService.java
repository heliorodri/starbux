package com.heliorodri.starbux.domain;

import static java.util.stream.Collectors.groupingBy;

import com.heliorodri.starbux.api.report.ReportMapper;
import com.heliorodri.starbux.api.report.ReportMostOrderToppingResponse;
import com.heliorodri.starbux.api.report.ReportTotalPerUserResponse;
import com.heliorodri.starbux.domain.topping.Topping;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import com.heliorodri.starbux.domain.user_drink.UserDrinkService;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportService {

  private final UserDrinkService userDrinkService;
  private final ReportMapper mapper;

  public ReportTotalPerUserResponse findTotalPerUser() {
    List<UserDrink> drinks = userDrinkService.findAll();
    Map<User, List<UserDrink>> totalPerUser = drinks.stream().collect(groupingBy(UserDrink::getUser));

    return mapper.toResponse(totalPerUser);
  }

  public ReportMostOrderToppingResponse findMostUsedToppings() {
    List<UserDrink> drinks = userDrinkService.findAll();
    Map<Long, List<Topping>> mostOrderedToping = drinks.stream()
        .map(UserDrink::getToppings)
        .flatMap(Collection::stream)
        .collect(groupingBy(Topping::getId));

    return mapper.toResponseTopping(mostOrderedToping);
  }

}
