package com.onlineshop.rest.service;

import com.onlineshop.rest.dao.entitys.Sale;
import com.onlineshop.rest.dao.jpa.SaleDao;
import com.onlineshop.rest.dto.SaleRequest;
import com.onlineshop.rest.dto.SaleResponse;
import com.onlineshop.rest.exception.NomenclatureIdNotFound;
import com.onlineshop.rest.exception.SaleNotFound;
import org.springframework.beans.BeanUtils;
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

    public SaleResponse createNewSales(SaleRequest saleRequest) throws NomenclatureIdNotFound {
        Sale sale = new Sale(
                userDetailService.getAuthenticationUser(),
                nomenclatureService.findById(
                    saleRequest.getNomenclature_id()),
                saleRequest.getAmount());
        return createSaleResponse(sale);
    }

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

    public SaleResponse updateById(
            long id,
            Sale saleFromDB,
            Sale sale)
                throws SaleNotFound {

        if (saleFromDB == null) {
            throw new SaleNotFound(id);
        }

        BeanUtils.copyProperties(
                sale,
                saleFromDB,
                "id");
        saleDao.save(sale);
        return createSaleResponse(sale);
    }

    public void deleteById(
            long id,
            Sale saleFromDB)
            throws SaleNotFound {

        if (saleFromDB == null) {
            throw new SaleNotFound(id);
        }
        saleDao.delete(saleFromDB);
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
                sale.getNomenclature().getName(),
                sale.getNomenclature().getDescription(),
                sale.getAmount(),
                sale.getUser().getUsername());
    }
}
