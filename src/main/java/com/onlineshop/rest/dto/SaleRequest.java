package com.onlineshop.rest.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class SaleRequest {

    @NotNull(message = "items не может быть NULL")
    @Size(min = 1, message = "items не может быть пустым")
    private Collection<ItemRequest> items;

}
