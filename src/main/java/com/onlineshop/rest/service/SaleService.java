package com.onlineshop.rest.service;

import com.onlineshop.rest.dao.entitys.Nomenclature;
import com.onlineshop.rest.dao.entitys.Sale;
import com.onlineshop.rest.dao.entitys.SaleItem;
import com.onlineshop.rest.dao.jpa.SaleDao;
import com.onlineshop.rest.dao.jpa.SaleItemDao;
import com.onlineshop.rest.dto.ItemRequest;
import com.onlineshop.rest.dto.SaleRequest;
import com.onlineshop.rest.dto.SaleResponse;
import com.onlineshop.rest.exception.NomenclatureIdNotFound;
import com.onlineshop.rest.exception.NomenclatureIdNotFoundList;
import com.onlineshop.rest.exception.SaleNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleDao saleDao;
    private final NomenclatureService nomenclatureService;
    private final UserDetailService userDetailService;
    private final SaleItemDao saleItemDao;

    @Autowired
    public SaleService(SaleDao saleDao, NomenclatureService nomenclatureService, UserDetailService userDetailService, SaleItemDao saleItemDao) {
        this.saleDao = saleDao;
        this.nomenclatureService = nomenclatureService;
        this.userDetailService = userDetailService;
        this.saleItemDao = saleItemDao;
    }

    /**
     * Обработка запросов клиентов
     */

    public List<SaleResponse> findAll() {

        List saleList = saleDao.findAll();

        return createSaleResponseList(saleList);
    }

    public SaleResponse findById(long id) throws SaleNotFound {

        Sale sale = saleDao.findById(id);

        if (sale == null) {
            throw new SaleNotFound(id);
        }
        return createSaleResponse(sale);
    }

    public List<SaleResponse> findByUser() {

        List saleList = saleDao.findByUser(
                userDetailService.getAuthenticationUser());

        return createSaleResponseList(saleList);
    }

    public void createNewSales(SaleRequest saleRequest) throws NomenclatureIdNotFound, NomenclatureIdNotFoundList {

        Sale sale = new Sale();
        List<SaleItem> saleItemList = newSaleItemList(sale, saleRequest);
        sale.setUser(userDetailService.getAuthenticationUser());

        saveSale(sale, saleItemList);
    }

    public void updateById(
            long id,
            Sale saleFromDB,
            SaleRequest saleRequest)
            throws SaleNotFound, NomenclatureIdNotFoundList, NomenclatureIdNotFound {

        if (saleFromDB == null) {
            throw new SaleNotFound(id);
        }

        List<SaleItem> saleItemList = newSaleItemList(saleFromDB, saleRequest);
        List<SaleItem> saleItemDeleteList = saleItemDao.findBySale(saleFromDB);

        updateSale(saleFromDB, saleItemList, saleItemDeleteList);
    }

    public void deleteById(
            long id,
            Sale saleFromDB)
            throws SaleNotFound {

        if (saleFromDB == null) {
            throw new SaleNotFound(id);
        }

        List<SaleItem> saleItemDeleteList = saleItemDao.findBySale(saleFromDB);
        deleteSale(saleFromDB, saleItemDeleteList);
    }


    /**
     * Сохранение и обновление данных продаж в информационной базе.
     */

    @Transactional
    public void saveSale(Sale sale, List<SaleItem> saleItemList) {
        saleDao.save(sale);
        for (SaleItem saleItem : saleItemList) {
            saleItemDao.save(saleItem);
        }
    }

    @Transactional
    public void updateSale(Sale sale, List<SaleItem> saleItemList, List<SaleItem> saleItemDeleteList) {

        for (SaleItem saleItem : saleItemDeleteList) {
            saleItemDao.delete(saleItem);
        }

        for (SaleItem saleItem : saleItemList) {
            saleItemDao.save(saleItem);
        }

        saleDao.save(sale);
    }

    @Transactional
    public void deleteSale(Sale sale, List<SaleItem> saleItemDeleteList) {
        for (SaleItem saleItem : saleItemDeleteList) {
            saleItemDao.delete(saleItem);
        }
        saleDao.delete(sale);
    }

    /**
     * Служебные методы класса.
     */

    private List<SaleItem> newSaleItemList(Sale sale, SaleRequest saleRequest)
            throws NomenclatureIdNotFound, NomenclatureIdNotFoundList {

        List<SaleItem> saleItemList = new ArrayList<>();
        List<String> errors = new ArrayList<>();

        for (ItemRequest item : saleRequest.getItems()) {

            Nomenclature nomenclature = nomenclatureService.findById(
                    item.getNomenclature_id());

            if (nomenclature == null) {
                errors.add(NomenclatureIdNotFoundList.newMessage(item.getNomenclature_id()));
            } else {
                saleItemList.add(
                        new SaleItem(
                                nomenclature,
                                item.getAmount(),
                                sale));
            }
        }

        if (!errors.isEmpty()) {
            throw new NomenclatureIdNotFoundList(errors);
        }

        return saleItemList;
    }

    private List<SaleResponse> createSaleResponseList(List saleList) {

        List<SaleResponse> saleResponseList = new ArrayList<>();

        for (Object item : saleList) {
            saleResponseList.add(createSaleResponse((Sale) item));
        }

        return saleResponseList;
    }

    private SaleResponse createSaleResponse(Sale sale) {
        return new SaleResponse(
                sale.getId(),
                sale.getUser().getUsername());
    }
}