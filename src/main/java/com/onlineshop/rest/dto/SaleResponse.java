package com.onlineshop.rest.dto;

import com.onlineshop.rest.dao.entitys.SaleItem;
import lombok.Data;

import java.util.Collection;

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
        this.userName = userName;
    }
}
