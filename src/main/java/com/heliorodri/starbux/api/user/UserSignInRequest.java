package com.heliorodri.starbux.api.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
public class UserSignInRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
