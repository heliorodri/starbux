package com.heliorodri.starbux.api.user;

import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.exception.InvalidOperationException;
import com.heliorodri.starbux.domain.role.Role;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user.UserRepository;
import com.heliorodri.starbux.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository repository;
    private final UserService service;
    private final UserMapper mapper;
    private final AuthenticationService authService;

    @ExceptionHandler(InvalidOperationException.class)
    @GetMapping()
    public ResponseEntity<List<UserResponse>> findAll(@RequestParam("token") String token) {
        try {
            authService.authenticate(token);

            final User user = authService.findUserByToken(token);

            if (user.getRole() != Role.ADMIN) {
                log.info("only admins can see the User list. Requested by token {}", token);
                return ResponseEntity.badRequest().build();
            }
            return new ResponseEntity<>(mapper.toResponseList(repository.findAll()), OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody @Valid UserDto dto) {
        try {
            User userToSignUp = mapper.toEntityRequest(dto);
            return new ResponseEntity<>(mapper.toReponse(service.signUp(userToSignUp)), OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserSignInRequest dto) {
        try {
            final User userToSignIn = mapper.toEntityRequest(dto);
            return new ResponseEntity<>(service.signIn(userToSignIn), OK);
        } catch (NoSuchAlgorithmException | InvalidOperationException e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

}
