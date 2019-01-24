package com.onlineshop.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "sales")
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
