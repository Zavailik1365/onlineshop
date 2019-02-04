package com.onlineshop.controller;

import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dto.ItemResponse;
import com.onlineshop.dto.SaleRequest;
import com.onlineshop.dto.SaleResponse;
import com.onlineshop.exception.NomenclatureIdNotFound;
import com.onlineshop.exception.NomenclatureIdNotFoundList;
import com.onlineshop.exception.SaleNotFound;
import com.onlineshop.service.SaleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Api(description = "Работа с продажами",
        consumes = "application/json")
public class SaleController {

    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @ApiOperation(
            value = "Получение списка всех продаж",
            notes = "Получение списка всех продаж с сервера."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 403, message = "отсутствуют права на получение списка всех продаж продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/admin/sales")
    public List<SaleResponse> salesList() {
        return saleService.findAll();
    }

    @ApiOperation(
            value = "Получение продажи по иденификатору",
            notes = "Получение продажи с сервера."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 403, message = "получение информации о продаже"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/admin/sale/{id}")
    public List<ItemResponse> saleById(
            @ApiParam(value = "идентификатор продажи", required = true) @PathVariable("id") long id)
            throws SaleNotFound {
        return saleService.findById(id);
    }

    @ApiOperation(
            value = "Создание новой продажи номенклатуры",
            notes = "Передача информации о новой продажи."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 400, message = "номенклатура по идентификатору не найдена"),
            @ApiResponse(code = 403, message = "отсутствуют права на формирование продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PostMapping(value = "rest-api/sale")
    public long createNewSale(@RequestBody @Valid SaleRequest saleRequest)
            throws NomenclatureIdNotFound, NomenclatureIdNotFoundList {
        return saleService.createNewSales(saleRequest);
    }


    @ApiOperation(
            value = "Обновление существующей продажи номенклатуры",
            notes = "Обновление информации о новой продажи."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "продажа по идентификатору не найдена"),
            @ApiResponse(code = 403, message = "отсутствуют права на обновление продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping(value = "rest-api/admin/sale/{id}")
    public void updateSaleById(
            @ApiParam(value = "идентификатор продажи", required = true) @PathVariable("id") long id,
            @PathVariable("id") Sale saleFromDB,
            @RequestBody @Valid SaleRequest saleRequest)
            throws SaleNotFound, NomenclatureIdNotFoundList, NomenclatureIdNotFound {
        saleService.updateById(id, saleFromDB, saleRequest);
    }

    @ApiOperation(
            value = "Удаление продажи",
            notes = "Удаление информации о продаже."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "продажа по идентификатору не найдена"),
            @ApiResponse(code = 403, message = "отсутствуют права на удаление продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @DeleteMapping(value = "rest-api/admin/sale/{id}")
    public void deleteSaleById(
            @ApiParam(value = "идентификатор продажи", required = true) @PathVariable("id") long id,
            @PathVariable("id") Sale saleFromDB)
            throws SaleNotFound {
        saleService.deleteById(id, saleFromDB);
    }

}
