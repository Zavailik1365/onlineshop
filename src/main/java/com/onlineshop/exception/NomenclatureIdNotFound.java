package com.onlineshop.exception;

public class NomenclatureIdNotFound extends Exception {

    private final static String MESSAGE_TEMPLATE =
            "Номенклатура с идентификатором %s не найдена.";

    public static String getMessageTemplate() {
        return MESSAGE_TEMPLATE;
    }

    private long id;

    public NomenclatureIdNotFound(long id) {
        super(String.format(MESSAGE_TEMPLATE, String.valueOf(id)));
        this.id = id;
    }
}
