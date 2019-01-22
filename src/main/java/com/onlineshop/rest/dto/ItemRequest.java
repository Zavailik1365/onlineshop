package com.onlineshop.rest.dto;

import com.onlineshop.rest.dao.entitys.SaleItem;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class ItemRequest {

    @NotNull(message = "nomenclature_id не может быть NULL")
    @Size(min = 1, message = "nomenclature_id не может быть пустым")
    private long nomenclature_id;

    @NotNull(message = "amount не может быть NULL")
    @Size(min = 1, message = "amount не может быть пустым")
    private long amount;

}
