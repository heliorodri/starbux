package com.heliorodri.starbux.domain.authentication;

import com.heliorodri.starbux.domain.exception.InvalidOperationException;
import com.heliorodri.starbux.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthenticationRepository repository;

    public Authentication saveToken(Authentication authentication){
        return repository.save(authentication);
    }

    public Authentication findByUser(User user) {
        return repository.findByUser(user);
    }

    public User findUserByToken(String token) {
        Authentication authentication = repository.findByToken(token);

        return (authentication != null && authentication.getUser() != null)
                ? authentication.getUser()
                : null;
    }

    public void authenticate(String token) {
        if (!StringUtils.hasText(token)) {
            throw new InvalidOperationException("Invalid Token. Authentication denied");
        }

        if (findUserByToken(token) == null) {
            throw new InvalidOperationException("Authentication denied! User not found");
        }
    }

}
