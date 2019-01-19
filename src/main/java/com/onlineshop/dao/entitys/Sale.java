package com.onlineshop.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "Sales")
@Data
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Nomenclature nomenclature;

    @NonNull
    private long amount;

    public Sale() {

    }

    public Sale(@NonNull User user, @NonNull Nomenclature nomenclature, long amount){
        this.user = user;
        this.nomenclature = nomenclature;
        this.amount = amount;
    }
}
