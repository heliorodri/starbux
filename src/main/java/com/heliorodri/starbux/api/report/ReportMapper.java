package com.heliorodri.starbux.api.report;

import com.heliorodri.starbux.api.topping.ToppingMapper;
import com.heliorodri.starbux.api.user.UserMapper;
import com.heliorodri.starbux.domain.topping.Topping;
import com.heliorodri.starbux.domain.topping.ToppingService;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user_drink.UserDrink;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportMapper {

  private final UserMapper userMapper;
  private final ToppingMapper toppingMapper;
  private final ToppingService toppingService;

  public ReportTotalPerUserResponse toResponse(Map<User, List<UserDrink>> totalPerUser) {

    List<ReportTotalPerUserDto> totalPerUserDto = totalPerUser.entrySet().stream()
        .map(userListEntry -> buildDto(userListEntry.getKey(), userListEntry.getValue().size()))
        .collect(Collectors.toList());

    return ReportTotalPerUserResponse.builder()
        .totalPerUser(totalPerUserDto)
        .build();
  }

  public ReportMostOrderToppingResponse toResponseTopping(Map<Long, List<Topping>> mostOrderedToppings) {
    List<ReportMostOrderToppingDto> mostOrdered = mostOrderedToppings.entrySet().stream()
        .map(longListEntry -> buildDto(longListEntry.getKey(), longListEntry.getValue().size()))
        .collect(Collectors.toList());

    return ReportMostOrderToppingResponse.builder()
        .mostOrderedToppings(mostOrdered)
        .build();
  }

  private ReportMostOrderToppingDto buildDto(Long id, Integer total){
    Topping topping = toppingService.findById(id);

    return ReportMostOrderToppingDto.builder()
        .topping(toppingMapper.toDto(topping))
        .totalOfOrders(total)
        .build();
  }

  private ReportTotalPerUserDto buildDto(User user, Integer total) {
    return ReportTotalPerUserDto.builder()
        .user(userMapper.toDto(user))
        .totalOfOrders(total)
        .build();
  }

}
