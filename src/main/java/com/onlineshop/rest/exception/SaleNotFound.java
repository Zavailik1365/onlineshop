package com.onlineshop.rest.exception;

public class SaleNotFound extends Exception {

    private final static String MESSAGE_TEMPLATE =
            "Продажа с идентификатором %s не найдена.";

    private long id;

    public SaleNotFound(long id) {
        super(String.format(MESSAGE_TEMPLATE, String.valueOf(id)));
        this.id = id;
    }
}
