package com.onlineshop.dto;

import com.onlineshop.dao.entitys.Role;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class UserResponse {

    /**
     * Логин пользователя
     */
    @NonNull
    private String name;

    /**
     * Полное имя пользователя
     */
    @NonNull
    private String fullname;

    /**
     * Email пользователя
     */
    @NonNull
    private String email;

    /**
     * Идентификатор пользователя
     */
    @NonNull
    private long id;

    /**
     * Признак активности пользователя
     */
    @NonNull
    private boolean active;

    /**
     * Набор прав доступа пользователя
     */
    @NonNull
    private Set<Role> roles;

}
