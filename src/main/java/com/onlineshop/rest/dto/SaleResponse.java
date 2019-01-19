package com.onlineshop.rest.dto;

import lombok.Data;

@Data
public class SaleResponse {

    /**
     * Идентификатор наименование номенклатуры не может быть пустым.
     */
    private String nomenclatureName;

    /**
     * Идентификатор наименование номенклатуры не может быть пустым.
     */
    private String nomenclatureDescription;

    /**
     * Количество проданных товаров не может быть равным 0.
     */
    private long amount;

    /**
     * Наименование пользователя купившиго номенклатуру.
     */
    private String userName;

    public SaleResponse(String nomenclatureName, String nomenclatureDescription, long amount, String userName) {
        this.nomenclatureName = nomenclatureName;
        this.nomenclatureDescription = nomenclatureDescription;
        this.amount = amount;
        this.userName = userName;
    }
}
