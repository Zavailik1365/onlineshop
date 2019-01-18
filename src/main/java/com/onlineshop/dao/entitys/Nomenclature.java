package com.onlineshop.dao.entitys;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@Data
@ToString(of = {"id", "name", "description"})
@EqualsAndHashCode(of = {"id"})
public class Nomenclature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String description;
}
