package com.onlineshop.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Nomenclatures")
@Data
public class Nomenclature {

    /**
     * Идентификатор номенклатуры.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Наименование номенклатуры.
     */
    @NonNull
    @Size(min = 2)
    private String name;

    /**
     * Описание номенклатуты для пользователя.
     */
    @NonNull
    @Size(min = 2)
    private String description;

    public Nomenclature() {

    }

    public Nomenclature(@NonNull String name, @NonNull String description) {
        this.name = name;
        this.description = description;
    }


}
