package com.heliorodri.starbux.api.report;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportTotalPerUserResponse {

  private List<ReportTotalPerUserDto> totalPerUser;

}
