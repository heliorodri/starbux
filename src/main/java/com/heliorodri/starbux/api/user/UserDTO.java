package com.heliorodri.starbux.api.user;

import com.heliorodri.starbux.domain.role.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDTO {

    private String name;
    private String email;
    private String password;
    private Role role;

}
