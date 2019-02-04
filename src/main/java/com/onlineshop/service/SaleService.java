package com.onlineshop.service;

import com.onlineshop.dao.entitys.Nomenclature;
import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dao.entitys.SaleItem;
import com.onlineshop.dao.entitys.User;
import com.onlineshop.dao.jpa.SaleDao;
import com.onlineshop.dao.jpa.SaleItemDao;
import com.onlineshop.dto.ItemRequest;
import com.onlineshop.dto.ItemResponse;
import com.onlineshop.dto.SaleRequest;
import com.onlineshop.dto.SaleResponse;
import com.onlineshop.exception.NomenclatureIdNotFound;
import com.onlineshop.exception.NomenclatureIdNotFoundList;
import com.onlineshop.exception.SaleNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SaleService {

    private final SaleDao saleDao;
    private final NomenclatureService nomenclatureService;
    private final UserDetailService userDetailService;
    private final SaleItemDao saleItemDao;

    @Autowired
    public SaleService(
            SaleDao saleDao,
            NomenclatureService nomenclatureService,
            UserDetailService userDetailService,
            SaleItemDao saleItemDao) {
        this.saleDao = saleDao;
        this.nomenclatureService = nomenclatureService;
        this.userDetailService = userDetailService;
        this.saleItemDao = saleItemDao;
    }

    /**
     * Обработка запросов клиентов
     */

    public  List<SaleResponse>  findByUser(User user) {

        List<Sale> sales = saleDao.findByUser(user);
        return  newSaleResponseList(sales);
    }

    public  List<SaleResponse>  findAll() {

        List<Sale> sales = saleDao.findAll();
        return  newSaleResponseList(sales);

    }

    public  List<ItemResponse> findById(long id) throws SaleNotFound {

        Sale sale = saleDao.findById(id);

        if (sale == null) {
            throw new SaleNotFound(id);
        }

        return newItemResponseList(sale);
    }

    public long createNewSales(SaleRequest saleRequest) throws NomenclatureIdNotFound, NomenclatureIdNotFoundList {

        Sale sale = new Sale();
        List<SaleItem> saleItemList = newSaleItemList(sale, saleRequest);
        sale.setUser(userDetailService.getAuthenticationUser());

        saveSale(sale, saleItemList);

        return sale.getId();
    }

    public void updateById(long id, Sale saleFromDB, SaleRequest saleRequest)
            throws SaleNotFound, NomenclatureIdNotFoundList, NomenclatureIdNotFound {

        if (saleFromDB == null) {
            throw new SaleNotFound(id);
        }

        List<SaleItem> saleItemList = newSaleItemList(saleFromDB, saleRequest);
        List<SaleItem> saleItemDeleteList = saleItemDao.findBySale(saleFromDB);

        updateSale(saleFromDB, saleItemList, saleItemDeleteList);
    }

    public void deleteById(long id, Sale saleFromDB) throws SaleNotFound {

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

    private List<SaleResponse> newSaleResponseList(List<Sale> sales){

        List<SaleResponse> saleResponse = new ArrayList<>();
        for (Object sale: sales){
            Sale saleEntity = (Sale) sale;
            saleResponse.add(new SaleResponse(
                    saleEntity.getId(),
                    saleEntity.getUser().getUsername()));
        }

        return saleResponse;
    }

    private List<ItemResponse> newItemResponseList(Sale sale){

        List<SaleItem> saleItems = saleItemDao.findBySale(sale);
        List<ItemResponse> saleItemsResponse = new ArrayList<>();
        for (Object saleItem: saleItems){
            SaleItem saleItemEntity = (SaleItem) saleItem;
            saleItemsResponse.add(
                    new ItemResponse(
                            saleItemEntity.getNomenclature().getName(),
                            saleItemEntity.getNomenclature().getId(),
                            saleItemEntity.getAmount()));
        }

        return saleItemsResponse;
    }
}