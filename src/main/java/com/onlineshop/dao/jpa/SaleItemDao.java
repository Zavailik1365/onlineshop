package com.onlineshop.dao.jpa;

import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dao.entitys.SaleItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleItemDao extends JpaRepository<SaleItem, Sale> {
    List<SaleItem> findBySale(Sale sale);
}
