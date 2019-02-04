package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemResponse {

    @NotNull(message = "nomenclatureName не может быть NULL")
    private String nomenclatureName;

    @NotNull(message = "nomenclatureId не может быть NULL")
    private long nomenclatureId;

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
