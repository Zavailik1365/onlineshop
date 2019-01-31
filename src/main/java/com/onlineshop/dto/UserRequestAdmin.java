package com.onlineshop.dto;

import com.onlineshop.dao.entitys.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class UserRequestAdmin {

    @NotNull(message = "username не может быть NULL")
    @Size(min = 1)
    private String username;

    @NotNull(message = "fullname не может быть NULL")
    @Size(min = 1)
    private String fullname;

    @NotNull(message = "email не может быть NULL")
    @Size(min = 1)
    @Pattern(regexp = "([A-z0-9_.-]{1,})@([A-z0-9_.-]{1,}).([A-z]{2,8})")
    private String email;

    private boolean active;
    private String password;
    private Set<Role> roles;

}
