package com.onlineshop.service;

import com.onlineshop.dao.entitys.Nomenclature;
import com.onlineshop.dao.jpa.NomenclatureDao;
import com.onlineshop.dao.jpa.SaleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NomenclatureService {

    private final NomenclatureDao nomenclatureDao;

    @Autowired
    public NomenclatureService(NomenclatureDao nomenclatureDao) {
        this.nomenclatureDao = nomenclatureDao;
    }

    public Nomenclature findById(long id){
        return nomenclatureDao.findById(id);
    }
}
