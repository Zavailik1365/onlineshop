package com.onlineshop.rest.dao.jpa;

import com.onlineshop.rest.dao.entitys.Sale;
import com.onlineshop.rest.dao.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDao extends JpaRepository<Sale, Long> {
    List<Sale> findByUser(User user);
    Sale findById(long id);
}
