package com.heliorodri.starbux.domain.user;

import com.heliorodri.starbux.domain.authentication.Authentication;
import com.heliorodri.starbux.domain.authentication.AuthenticationService;
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

    public User signUp(User userToSignUp) {
        if (repository.findByEmail(userToSignUp.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        String encryptedPassword = userToSignUp.getPassword();
        try {
            encryptedPassword = hashPassword(userToSignUp.getPassword());
        } catch (NoSuchAlgorithmException e) {
            log.error("hashing password failed {}", e.getMessage());
        }

        User user = User.builder()
                .name(userToSignUp.getName())
                .email(userToSignUp.getEmail())
                .role(userToSignUp.getRole())
                .password(encryptedPassword)
                .build();

        try {
            final User createdUser = repository.save(user);
            final Authentication authenticationToken = new Authentication(createdUser);
            authenticationService.saveToken(authenticationToken);

            return createdUser;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String signIn(User userToSignIn) {
        User user = repository.findByEmail(userToSignIn.getEmail());
        if(user == null){
            throw new RuntimeException("user not present");
        }

        try {
            if (!user.getPassword().equals(hashPassword(userToSignIn.getPassword()))){
                throw new RuntimeException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            log.error("hashing password failed {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

        Authentication authentication = authenticationService.findByUser(user);

        if(authentication == null) {
            throw new RuntimeException("token not found");
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
