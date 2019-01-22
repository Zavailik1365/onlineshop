package com.onlineshop.rest.dao.entitys;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Nomenclatures")
@Data
public class Nomenclature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    /**
     * Наименование номенклатуры.
     */
    @NonNull
    @Size(min = 1)
    private String name;

    /**
     * Описание номенклатуты для пользователя.
     */
    @NonNull
    @Size(min = 1)
    private String description;

    /**
     * Признак доступности номенклатуры для покупки.
     */
    private boolean isActive;

    public Nomenclature() {

    }

    public Nomenclature(@NonNull String name, @NonNull String description, boolean isActive) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }


}
