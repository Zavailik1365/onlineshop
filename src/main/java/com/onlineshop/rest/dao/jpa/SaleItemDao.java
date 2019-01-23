package com.onlineshop.rest.dao.jpa;

import com.onlineshop.rest.dao.entitys.Sale;
import com.onlineshop.rest.dao.entitys.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemDao extends JpaRepository<SaleItem, Sale> {
    List<SaleItem> findBySale(Sale sale);
}
