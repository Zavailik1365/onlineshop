package com.onlineshop.rest.dao.jpa;

import com.onlineshop.rest.dao.entitys.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NomenclatureDao extends JpaRepository<Nomenclature, Long> {
    Nomenclature findById(long id);
}
