package com.onlineshop.service;

import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dao.entitys.SaleItem;
import com.onlineshop.dao.jpa.SaleDao;
import com.onlineshop.dao.jpa.SaleItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SaleItemService {

    private final SaleItemDao saleItemDao;
    private final SaleDao saleDao;

    @Autowired
    public SaleItemService(SaleItemDao saleItemDao, SaleDao saleDao) {
        this.saleItemDao = saleItemDao;
        this.saleDao = saleDao;
    }

    @Transactional
    public void saveSaleItems(Sale sale, List<SaleItem> saleItems) {
        for (SaleItem saleItem: saleItems){
            saleItemDao.save(saleItem);
        }
        saleDao.save(sale);
    }
}
