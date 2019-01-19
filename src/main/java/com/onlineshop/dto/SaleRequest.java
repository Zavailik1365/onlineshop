package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SaleRequest {

    /**
     * Идентификатор номенклатуры не может быть пустым.
     */
    @NotNull(message = "nomenclature_id не может быть NULL")
    @Size(min = 1, message = "nomenclature_id не может быть равен 0")
    private Long nomenclature_id;

    /**
     * Количество проданных товаров не может быть равным 0.
     */
    @NotNull(message = "amount не может быть NULL")
    @Size(min = 1, message = "amount не может быть равен 0")
    private Long amount;
}
