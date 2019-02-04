package com.onlineshop.dao.jpa;

import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dao.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDao extends JpaRepository<Sale, Long> {
    Sale findById(long Id);
}
