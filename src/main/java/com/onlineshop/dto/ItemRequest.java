package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

@Data
public class ItemRequest {

    @NotNull(message = "nomenclature_id не может быть NULL")
    @DecimalMin(value = "1")
    private long nomenclature_id;

    @NotNull(message = "amount не может быть NULL")
    @DecimalMin(value = "1")
    private long amount;

}
