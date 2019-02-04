package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserRequest {

    /**
     * Логин пользователя
     */
    @NotNull(message = "username не может быть NULL")
    @Size(min = 2)
    private String username;

    /**
     * Полное имя пользователя
     */
    @NotNull(message = "fullname не может быть NULL")
    @Size(min = 2)
    private String fullname;

    /**
     * Email пользователя
     */
    @NotNull(message = "email не может быть NULL")
    @Size(min = 1)
    @Pattern(regexp = "([A-z0-9_.-]{1,})@([A-z0-9_.-]{1,}).([A-z]{2,8})")
    private String email;

    /**
     * Пароль пользователея
     */
    private String password;

}
