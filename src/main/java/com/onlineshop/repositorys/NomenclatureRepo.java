package com.onlineshop.repositorys;

import com.onlineshop.domain.Nomenclature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NomenclatureRepo extends JpaRepository<Nomenclature, Long> {

}