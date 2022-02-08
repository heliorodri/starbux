package com.heliorodri.starbux.api.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Component
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSignInRequest {

    @NotNull
    private String email;

    @NotNull
    private String password;

}
