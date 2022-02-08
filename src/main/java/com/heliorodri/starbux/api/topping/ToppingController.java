package com.heliorodri.starbux.api.topping;

import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.topping.ToppingService;
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

import java.util.List;

import static com.heliorodri.starbux.domain.role.Role.ADMIN;

@RestController
@RequestMapping("/toppings")
@RequiredArgsConstructor
@Slf4j
public class ToppingController {

    private final ToppingService service;
    private final ToppingMapper mapper;
    private final AuthenticationService authService;

    @PostMapping
    public ResponseEntity<ToppingDto> create(@RequestParam("token") String token, @RequestBody ToppingCreateRequest dto) {
        authService.authenticate(token);
        User user = authService.findUserByToken(token);

        if (user.getRole() != ADMIN) {
            log.info("You must be an ADMIN to create toppings. Requested by token {}", token);
            return ResponseEntity.badRequest().build();
        }

        return new ResponseEntity<>(mapper.toDto(service.create(mapper.toEntity(dto))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@RequestParam("token") String token, @PathVariable("id") Long id) {
        authService.authenticate(token);
        User user = authService.findUserByToken(token);

        if (user.getRole() != ADMIN) {
            log.info("You must be an ADMIN to delete toppings. Requested by token {}", token);
            return ResponseEntity.badRequest().build();
        }

        service.deleteById(id);
        return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<List<ToppingDto>> findAll(@RequestParam("token") String token) {
        try {
            authService.authenticate(token);

            return new ResponseEntity<>(mapper.toDtoList(service.findAll()), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }



}
