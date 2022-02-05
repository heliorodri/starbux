package com.heliorodri.starbux.api.topping;

import com.heliorodri.starbux.domain.topping.ToppingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/toppings")
@RequiredArgsConstructor
public class ToppingController {

    private final ToppingService service;
    private final ToppingMapper mapper;

    @PostMapping
    public ResponseEntity<ToppingDto> create(@RequestBody ToppingDto dto) {
        return new ResponseEntity<>(mapper.toDto(service.create(mapper.toEntity(dto))), HttpStatus.CREATED);
    }


}
