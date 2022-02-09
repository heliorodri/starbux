package com.heliorodri.starbux.api.report;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportMostOrderToppingResponse {

  List<ReportMostOrderToppingDto> mostOrderedToppings;

}
