package com.heliorodri.starbux.api.drink;

import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.drink.DrinkService;
import com.heliorodri.starbux.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

import static com.heliorodri.starbux.domain.role.Role.ADMIN;

@RestController
@RequestMapping("/drinks")
@RequiredArgsConstructor
@Slf4j
public class DrinkController {

    private final DrinkService service;
    private final DrinkMapper mapper;
    private final AuthenticationService authService;

    @PostMapping()
    public ResponseEntity<DrinkDto> create(@RequestParam("token") String token,
                                           @RequestBody @Valid DrinkCreateRequest request) {
        authService.authenticate(token);
        User user = authService.findUserByToken(token);

        if (user.getRole() != ADMIN) {
            log.info("You must be an ADMIN to create drinks. Requested by token {}", token);
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(mapper.toDto(service.save(mapper.toEntity(request))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@RequestParam("token") String token, @PathVariable @NotNull Long id) {
        authService.authenticate(token);
        User user = authService.findUserByToken(token);

        if (user.getRole() != ADMIN) {
            log.info("You must be an ADMIN to delete drinks. Requested by token {}", token);
            return ResponseEntity.badRequest().build();
        }

        service.delete(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<DrinkDto>> findAll(@RequestParam("token") String token) {
        try {
            authService.authenticate(token);
            return new ResponseEntity<>(service.findAll().stream().map(mapper::toDto).collect(Collectors.toList()), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
