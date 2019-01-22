package com.onlineshop.rest.exception;

import java.util.List;

public class NomenclatureIdNotFoundList extends Exception {

    public static String newMessage(long id){
        return String.format(
                "Номенклатура с идентификатором %s не найдена.",
                String.valueOf(id));
    }

    public NomenclatureIdNotFoundList(List<String> messages) {
        super(String.join(",", messages));
    }
}
