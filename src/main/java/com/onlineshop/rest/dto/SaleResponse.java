package com.onlineshop.rest.dto;

import com.onlineshop.rest.dao.entitys.SaleItem;
import lombok.Data;

import java.util.Collection;

@Data
public class SaleResponse {

    /**
     * Список приобритенных товаров.
     */
    private Collection<SaleItem> saleItems;

    /**
     * Наименование пользователя купившиго номенклатуру.
     */
    private String userName;

    public SaleResponse(){

    }

    public SaleResponse(Collection<SaleItem> saleItem, String userName) {
        this.saleItems = saleItem;
        this.userName = userName;
    }
}
