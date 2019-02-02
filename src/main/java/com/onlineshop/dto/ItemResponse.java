package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ItemResponse {

    @NotNull(message = "name не может быть NULL")
    private String name;

    @NotNull(message = "amount не может быть NULL")
    private long amount;

    public ItemResponse(
            @NotNull(message = "name не может быть NULL") String name,
            @NotNull(message = "amount не может быть NULL") long amount) {
        this.name = name;
        this.amount = amount;
    }
}
