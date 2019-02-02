package com.onlineshop.dto;

import com.onlineshop.dao.entitys.SaleItem;
import lombok.Data;

import java.util.List;

@Data
public class SaleResponse {

    /**
     * Список приобритенных товаров.
     */
    private long id;

    /**
     * Наименование пользователя купившиго номенклатуру.
     */
    private String userName;

    public SaleResponse(){

    }

    public SaleResponse(long id, String userName) {
        this.id = id;
        this.userName = String.format("Создал продажу пользователь %s", userName);
    }
}
