package com.onlineshop.rest.exception;

public class UserNotFound extends Exception {

    private final static String MESSAGE_TEMPLATE =
            "Пользователь с идентификатором %s не найдена.";

    private long id;

    public UserNotFound(long id) {
        super(String.format(MESSAGE_TEMPLATE, String.valueOf(id)));
        this.id = id;
    }
}
