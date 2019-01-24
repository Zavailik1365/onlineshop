package com.onlineshop.exception;

import java.util.List;

public class NomenclatureIdNotFoundList extends Exception {

    public static String newMessage(long id){
        NomenclatureIdNotFound nomenclatureIdNotFound = new NomenclatureIdNotFound(id);
        return String.format(
                nomenclatureIdNotFound.getMessageTemplate(),
                String.valueOf(id));
    }

    public NomenclatureIdNotFoundList(List<String> messages) {
        super(String.join(",", messages));
    }
}
