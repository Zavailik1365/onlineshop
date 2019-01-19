package com.onlineshop.service;

import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dao.jpa.SaleDao;
import com.onlineshop.dto.SaleRequest;
import com.onlineshop.dto.SaleResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SaleService {

    private final SaleDao saleDao;
    private final NomenclatureService nomenclatureService;
    private final UserDetailService userDetailService;

    @Autowired
    public SaleService(SaleDao saleDao,
                       NomenclatureService nomenclatureService,
                       UserDetailService userDetailService) {
        this.saleDao = saleDao;
        this.nomenclatureService = nomenclatureService;
        this.userDetailService = userDetailService;
    }

    public Sale createNewSales(SaleRequest saleRequest) {
        Sale sale = new Sale(
                userDetailService.getAuthenticationUser(),
                nomenclatureService.findById(
                    saleRequest.getNomenclature_id()),
                saleRequest.getAmount());
        return saleDao.save(sale);
    }

    public List<SaleResponse> findAll() {

        List saleList = saleDao.findAll();

        return createSaleResponse(saleList);
    }

    public List<SaleResponse> findByUser() {

        List saleList = saleDao.findByUser(
                userDetailService.getAuthenticationUser());

        return createSaleResponse(saleList);
    }

    private List<SaleResponse> createSaleResponse(List saleList) {

        List saleResponseList = new ArrayList<SaleResponse>();

        for (Object item : saleList) {
            Sale sale = (Sale) item;
            saleResponseList.add(new SaleResponse(
                    sale.getNomenclature().getName(),
                    sale.getNomenclature().getDescription(),
                    sale.getAmount(),
                    sale.getUser().getUsername()));
        }

        return saleResponseList;
    }
}
