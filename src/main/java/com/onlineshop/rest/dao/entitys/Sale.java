package com.onlineshop.rest.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.util.Collection;

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

    public Sale() {

    }

    public Sale(@NonNull User user){
        this.user = user;
    }
}
