package com.onlineshop.service;

import com.onlineshop.dao.entitys.Nomenclature;
import com.onlineshop.dao.jpa.NomenclatureDao;
import com.onlineshop.exception.NomenclatureAlreadyExist;
import com.onlineshop.exception.NomenclatureIdNotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NomenclatureService {

    private final NomenclatureDao nomenclatureDao;

    @Autowired
    public NomenclatureService(NomenclatureDao nomenclatureDao) {
        this.nomenclatureDao = nomenclatureDao;
    }

    public Nomenclature findById(long id) throws NomenclatureIdNotFound {

        Nomenclature nomenclature = nomenclatureDao.findById(id);
        if (nomenclature == null) {
            throw new NomenclatureIdNotFound(id);
        }

        return nomenclature;
    }

    public List<Nomenclature> findAll() {
        return nomenclatureDao.findAll();
    }

    public Nomenclature create(Nomenclature nomenclature) throws NomenclatureAlreadyExist {

        Nomenclature nomenclatureDB = nomenclatureDao.findById(nomenclature.getId());
        if (nomenclatureDB != null) {
            throw new NomenclatureAlreadyExist(nomenclature.getId());
        }

        return nomenclatureDao.save(nomenclature);
    }

    public Nomenclature update(
            long id,
            Nomenclature nomenclatureFromDB,
            Nomenclature nomenclature)
                throws NomenclatureIdNotFound {

        if (nomenclatureFromDB == null) {
            throw new NomenclatureIdNotFound(id);
        }

        BeanUtils.copyProperties(
                nomenclature,
                nomenclatureFromDB,
                "id");

        return nomenclatureDao.save(nomenclatureFromDB);
    }

}
