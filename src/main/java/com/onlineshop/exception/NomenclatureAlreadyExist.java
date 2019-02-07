package com.onlineshop.exception;

public class NomenclatureAlreadyExist extends Exception {

    private final static String MESSAGE_TEMPLATE =
            "Номенклатура с идентификатором %s уже существует.";

    public NomenclatureAlreadyExist(long id) {
        super(String.format(MESSAGE_TEMPLATE, String.valueOf(id)));
    }
}
