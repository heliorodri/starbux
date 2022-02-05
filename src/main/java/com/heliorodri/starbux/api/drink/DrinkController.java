package com.heliorodri.starbux.api.drink;

import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.drink.DrinkService;
import com.heliorodri.starbux.domain.user.User;
import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService service;
    private final DrinkMapper mapper;
    private final AuthenticationService authService;

    @PostMapping()
    public DrinkDto create(@RequestParam("token") String token, @RequestBody @Valid DrinkDto request) {
        authService.authenticate(token);
        User user = authService.findUserByToken(token);

        return mapper.toDto(service.save(mapper.toEntity(request), user));
    }

    @DeleteMapping("/{id}")
    public void delete(@RequestParam("token") String token, @PathVariable @NotNull Long id) {
        authService.authenticate(token);
        User user = authService.findUserByToken(token);

        service.delete(id, user);
    }

    @GetMapping()
    public List<DrinkDto> findAll(@RequestParam("token") String token) {
        authService.authenticate(token);
        return service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

}
