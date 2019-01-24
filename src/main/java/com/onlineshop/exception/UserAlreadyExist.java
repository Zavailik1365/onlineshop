package com.onlineshop.exception;

public class UserAlreadyExist extends Exception {

    private final static String MESSAGE_TEMPLATE =
            "Пользователь с логином %s уже существует.";

    private long id;

    public UserAlreadyExist(String username) {
        super(String.format(MESSAGE_TEMPLATE, username));
        this.id = id;
    }
}
