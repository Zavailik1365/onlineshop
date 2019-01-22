package com.onlineshop.rest.dao.jpa;

import com.onlineshop.rest.dao.entitys.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleItemDao extends JpaRepository<SaleItem, Long> {

}
