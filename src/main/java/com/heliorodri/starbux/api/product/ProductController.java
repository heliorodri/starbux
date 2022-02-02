package com.heliorodri.starbux.api.product;

import com.heliorodri.starbux.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping()
    public ProductDto create(@RequestBody @Valid ProductDto request) {
        return mapper.toDto(service.create(mapper.toEntity(request)));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable @NotNull Long id) {
        service.delete(id);
    }

    @GetMapping()
    public List<ProductDto> findAll() {
        return service.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

}
