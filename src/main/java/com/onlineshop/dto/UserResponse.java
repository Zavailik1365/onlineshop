package com.onlineshop.dto;

import com.onlineshop.dao.entitys.Role;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UserResponse {

    @NonNull
    private String name;

    @NonNull
    private String fullname;

    @NonNull
    private String email;

    @NonNull
    private long id;

    @NonNull
    private boolean active;

    @NonNull
    private Set<Role> roles;

}
