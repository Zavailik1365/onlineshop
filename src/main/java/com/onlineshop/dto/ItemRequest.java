package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class ItemRequest {

    /**
     * Идентификатор номенклатуры
     */
    @NotNull(message = "nomenclature_id не может быть NULL")
    @DecimalMin(value = "1")
    private long nomenclature_id;

    /**
     * Количество купленного товара
     */
    @NotNull(message = "amount не может быть NULL")
    @DecimalMin(value = "1")
    private long amount;

}
