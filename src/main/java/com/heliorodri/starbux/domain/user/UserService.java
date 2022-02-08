package com.heliorodri.starbux.domain.user;

import com.heliorodri.starbux.domain.authentication.Authentication;
import com.heliorodri.starbux.domain.authentication.AuthenticationService;
import com.heliorodri.starbux.domain.exception.InvalidOperationException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository repository;
    private final AuthenticationService authenticationService;

    public User signUp(@NonNull User userToSignUp) throws NoSuchAlgorithmException, InvalidOperationException {
        if (repository.findByEmail(userToSignUp.getEmail()) != null) {
            throw new InvalidOperationException("User already exists");
        }

        String encryptedPassword = hashPassword(userToSignUp.getPassword());

        User user = User.builder()
                .name(userToSignUp.getName())
                .email(userToSignUp.getEmail())
                .role(userToSignUp.getRole())
                .password(encryptedPassword)
                .build();

        final User createdUser = repository.save(user);
        final Authentication authenticationToken = new Authentication(createdUser);
        authenticationService.saveToken(authenticationToken);

        return createdUser;
    }

    public String signIn(@NonNull User userToSignIn) throws NoSuchAlgorithmException {
        User user = repository.findByEmail(userToSignIn.getEmail());
        if(user == null){
            throw new InvalidOperationException("user not found");
        }

        if (!user.getPassword().equals(hashPassword(userToSignIn.getPassword()))){
            throw new InvalidOperationException("Wrong password");
        }

        Authentication authentication = authenticationService.findByUser(user);

        if(authentication == null) {
            throw new InvalidOperationException("token not found");
        }

        return authentication.getToken();
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}
