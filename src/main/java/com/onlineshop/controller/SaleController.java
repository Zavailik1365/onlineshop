package com.onlineshop.controller;

import com.onlineshop.dao.entitys.Sale;
import com.onlineshop.dto.SaleRequest;
import com.onlineshop.dto.SaleResponse;
import com.onlineshop.service.SaleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class SaleController {


    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }


    @ApiOperation(
            value = "Получение списка всех покупок номенклатуры",
            notes = "Получение списка всех продаж с сервера."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 403, message = "отсутствуют права на формирование продажи"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("admin/users/sale")
    public List<SaleResponse> list() {
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

    // TODO написать описание ошибок и метода
    @PostMapping("/sale")
    public Sale createNewSale(@RequestBody SaleRequest saleRequest) {
        return saleService.createNewSales(saleRequest);
    }

}
