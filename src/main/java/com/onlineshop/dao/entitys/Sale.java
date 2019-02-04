package com.onlineshop.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "sales")
@Data
public class Sale {

    /**
     * Идентификатор продажи.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Пользователь сформировавший продажу.
     */
    @NonNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public Sale() {

    }

    public Sale(@NonNull User user){
        this.user = user;
    }
}
