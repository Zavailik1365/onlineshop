package com.onlineshop.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SaleRequest {

    /**
     * Список товаров
     */
    @NotNull(message = "items не может быть NULL")
    @Size(min = 1, message = "items не может быть пустым")
    private List<ItemRequest> items;

}
