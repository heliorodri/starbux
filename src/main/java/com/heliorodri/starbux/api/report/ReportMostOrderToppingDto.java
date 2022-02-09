package com.heliorodri.starbux.api.report;

import com.heliorodri.starbux.api.topping.ToppingDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportMostOrderToppingDto {

  private ToppingDto topping;
  private Integer totalOfOrders;

}
