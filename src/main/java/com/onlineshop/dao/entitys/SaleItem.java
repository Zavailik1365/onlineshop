package com.onlineshop.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Size;

@Entity
@Table(name = "sale_item")
@Data
public class SaleItem {

    /**
     * Идентификатор строки продажи
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Проданная номенклатура
     */
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Nomenclature nomenclature;

    /**
     * Количество проданной номенклатуры
     */
    @NonNull
    @DecimalMin(value = "1")
    private long amount;

    /**
     * Продажа к которой относится строка
     */
    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Sale sale;

    public SaleItem(){

    }

    public SaleItem(@NonNull Nomenclature nomenclature, @NonNull @Size(min = 1) long amount, @NonNull Sale sale) {
        this.nomenclature = nomenclature;
        this.amount = amount;
        this.sale = sale;

    }
}
