package com.onlineshop.dto;

import lombok.Data;

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
