package com.heliorodri.starbux.domain.user;

import com.heliorodri.starbux.api.user.UserDTO;
import com.heliorodri.starbux.api.user.UserSignInReponse;
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

    public User signUp(UserDTO dto) {
        if (repository.findByEmail(dto.getEmail()) != null) {
            throw new RuntimeException("User already exists");
        }

        String encryptedPassword = dto.getPassword();
        try {
            encryptedPassword = hashPassword(dto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            log.error("hashing password failed {}", e.getMessage());
        }

        User user = User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .role(dto.getRole())
                .password(encryptedPassword)
                .build();

        try {
            User createdUser = repository.save(user);
            final Authentication authenticationToken = new Authentication(createdUser);
            authenticationService.saveToken(authenticationToken);

            return createdUser;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserSignInReponse signIn(UserDTO dto) {
        User user = repository.findByEmail(dto.getEmail());
        if(user == null){
            throw new RuntimeException("user not present");
        }

        try {
            if (!user.getPassword().equals(hashPassword(dto.getPassword()))){
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

        return UserSignInReponse.builder()
                .status("sucess")
                .token(authentication.getToken())
                .build();
    }

    String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}
