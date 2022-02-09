package com.heliorodri.starbux.api.report;

import com.heliorodri.starbux.domain.ReportService;
import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Slf4j
public class ReportController {

  private final ReportService service;
  private final AuthenticationService authService;

  @GetMapping("/total-per-customer")
  public ResponseEntity<ReportTotalPerUserResponse> findTotalPerCustomer(@RequestParam("token") String token) {
    authService.authenticate(token);
    User user = authService.findUserByToken(token);

    if (user.getRole() != Role.ADMIN) {
      log.error("You must be an ADMIN to see reports. Request sent by token {} ", token);
      return ResponseEntity.badRequest().build();
    }

    return new ResponseEntity<>(service.findTotalPerUser(), HttpStatus.OK);
  }

  @GetMapping("/most-ordered-toppings")
  public ResponseEntity<ReportMostOrderToppingResponse> findMostOrderedToppings(@RequestParam("token") String token) {
    authService.authenticate(token);
    User user = authService.findUserByToken(token);

    if (user.getRole() != Role.ADMIN) {
      log.error("You must be an ADMIN to see reports. Request sent by token {} ", token);
      return ResponseEntity.badRequest().build();
    }

    return new ResponseEntity<>(service.findMostUsedToppings(), HttpStatus.OK);

  }

}
