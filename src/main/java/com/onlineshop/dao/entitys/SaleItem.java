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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Nomenclature nomenclature;

    @NonNull
    @DecimalMin(value = "1")
    private long amount;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Sale sale;

    public SaleItem(){

    }

    public SaleItem(@NonNull Nomenclature nomenclature, @NonNull @Size(min = 1) long amount, @NonNull Sale sale) {
        this.nomenclature = nomenclature;
        this.amount = amount;
        this.sale = sale;

    }
}
