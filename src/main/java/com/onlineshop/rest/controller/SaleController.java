package com.onlineshop.rest.controller;

import com.onlineshop.rest.dao.entitys.Sale;
import com.onlineshop.rest.dto.SaleRequest;
import com.onlineshop.rest.dto.SaleResponse;
import com.onlineshop.rest.exception.NomenclatureIdNotFound;
import com.onlineshop.rest.exception.SaleNotFound;
import com.onlineshop.rest.service.SaleService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Api(description = "Работа с продажами", consumes = "application/json")
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
    @GetMapping("/admin/sales")
    public List<SaleResponse> salesList() {
        return saleService.findAll();
    }

    @ApiOperation(
            value = "Получение списка всех покупок номенклатуры для текущего пользователя",
            notes = "Получение списка всех покупок пользователя с сервера."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 403, message = "отсутствуют права на формирование продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("/sale")
    public List<SaleResponse> listByUser() {
        return saleService.findByUser();
    }

    @ApiOperation(
            value = "Создание новой продажи номенклатуры",
            notes = "Передача информации о новой продажи.."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "продажа по идентификатору не найдена"),
            @ApiResponse(code = 403, message = "отсутствуют права на формирование продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("/admin/sale/{id}")
    public SaleResponse getSaleById(
            @ApiParam(value = "идентификатор продажи", required = true) @PathVariable("id") long id)
            throws SaleNotFound {
        return saleService.findById(id);
    }

    @ApiOperation(
            value = "Создание новой продажи номенклатуры",
            notes = "Передача информации о новой продажи.."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 400, message = "номенклатура по идентификатору не найдена"),
            @ApiResponse(code = 403, message = "отсутствуют права на формирование продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PostMapping("/sale")
    public SaleResponse createNewSale(@RequestBody SaleRequest saleRequest)
            throws NomenclatureIdNotFound {
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
    @PutMapping("admin/sale/{id}")
    public SaleResponse updateSaleById(
            @ApiParam(value = "идентификатор продажи", required = true) @PathVariable("id") long id,
            @PathVariable("id") Sale saleFromDB,
            @RequestBody Sale sale)
            throws SaleNotFound {
        return saleService.updateById(id, saleFromDB, sale);
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
    @DeleteMapping("admin/sale/{id}")
    public void deleteSaleById(
            @ApiParam(value = "идентификатор продажи", required = true) @PathVariable("id") long id,
            @PathVariable("id") Sale saleFromDB)
            throws SaleNotFound {
        saleService.deleteById(id, saleFromDB);
    }

}
