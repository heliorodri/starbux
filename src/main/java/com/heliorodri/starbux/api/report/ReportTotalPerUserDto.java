package com.heliorodri.starbux.api.report;

import com.heliorodri.starbux.api.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTotalPerUserDto {

  private UserDto user;
  private Integer totalOfOrders;

}
