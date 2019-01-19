package com.onlineshop.rest.dto;

import com.onlineshop.rest.dao.entitys.Role;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UserResponse {

    @NonNull
    private String name;

    @NonNull
    private long id;

    @NonNull
    private boolean active;

    @NonNull
    private Set<Role> roles;

}
