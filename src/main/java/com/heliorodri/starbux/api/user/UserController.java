package com.heliorodri.starbux.api.user;

import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.user.User;
import com.heliorodri.starbux.domain.user.UserRepository;
import com.heliorodri.starbux.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;
    private final UserService service;
    private final UserMapper mapper;
    private final AuthenticationService authService;

    @GetMapping()
    public List<User> findAll(@RequestParam("token") String token) {
        authService.authenticate(token);
        return repository.findAll();
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody @Valid UserDto dto) {
        User userToSignUp = mapper.toEntity(dto);
        return new ResponseEntity<>(mapper.toDto(service.signUp(userToSignUp)), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody @Valid UserSignInRequest dto) {
        User userToSignIn = mapper.toEntity(dto);
        return new ResponseEntity<>(service.signIn(userToSignIn), HttpStatus.OK);
    }

}
