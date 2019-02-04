package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemResponse {

    /**
     * Наименоание номенклатуры
     */
    @NotNull(message = "nomenclatureName не может быть NULL")
    private String nomenclatureName;

    /**
     * Идентификатор номенклатуры
     */
    @NotNull(message = "nomenclatureId не может быть NULL")
    private long nomenclatureId;

    /**
     * Количество купленного товара
     */
    @NotNull(message = "amount не может быть NULL")
    private long amount;

    public ItemResponse(
            @NotNull(message = "nomenclatureName не может быть NULL") String nomenclatureName,
            @NotNull(message = "nomenclatureId не может быть NULL") long nomenclatureId,
            @NotNull(message = "amount не может быть NULL") long amount) {
        this.nomenclatureName = nomenclatureName;
        this.nomenclatureId = nomenclatureId;
        this.amount = amount;
    }
}
