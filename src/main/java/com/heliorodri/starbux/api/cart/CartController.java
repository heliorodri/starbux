package com.heliorodri.starbux.api.cart;

import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.cart.CartService;
import com.heliorodri.starbux.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService service;
    private final CartMapper mapper;
    private final AuthenticationService authService;

    @PostMapping("/add")
    public ResponseEntity<CartItemsDto> add(@RequestParam("token") String token, @RequestBody CartItemRequest dto) {
        try {
            authService.authenticate(token);
            User user = authService.findUserByToken(token);

            return new ResponseEntity<>(service.addProduct(mapper.toEntity(dto, user)), CREATED);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping()
    public ResponseEntity<CartItemsDto> getCart(@RequestParam("token") String token) {
        try {
            authService.authenticate(token);
            User user = authService.findUserByToken(token);

            return new ResponseEntity<>(service.listItems(user), OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update-item")
    public ResponseEntity<CartItemsDto> updateItem(@RequestParam("token") String token, @RequestBody CartItemRequest request) {
        try {
            authService.authenticate(token);
            User user = authService.findUserByToken(token);

            return new ResponseEntity<>(service.updateItem(mapper.toEntity(request, user), user), OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete-item/{itemId}")
    public ResponseEntity<CartItemsDto> deleteItem(@RequestParam("token") String token, @PathVariable Long itemId) {
        try {
            authService.authenticate(token);
            User user = authService.findUserByToken(token);

            return new ResponseEntity<>(service.deleteItem(itemId, user), NO_CONTENT);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }


}
