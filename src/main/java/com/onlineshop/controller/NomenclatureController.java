package com.onlineshop.controller;

import com.onlineshop.dao.entitys.Nomenclature;
import com.onlineshop.exception.NomenclatureAlreadyExist;
import com.onlineshop.exception.NomenclatureIdNotFound;
import com.onlineshop.service.NomenclatureService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping
@Api(description = "Работа с номенклатурой",
        consumes = "application/json")
public class NomenclatureController {

    private final  NomenclatureService nomenclatureService;

    @Autowired
    public NomenclatureController(NomenclatureService nomenclatureService) {
        this.nomenclatureService = nomenclatureService;
    }

    @ApiOperation(
            value = "Получение списка всех номенклатур",
            notes = "Получение списка всех номенклатур с сервера."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/nomenclatures")
    public List<Nomenclature> list() {
        return nomenclatureService.findAll();
    }

    @ApiOperation(
            value = "Получение номенклатуры по идентфикатору",
            notes = "Получение номенклатуры по идентфикатору с сервера."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 404, message = "номенклатура по идентификатору не найдена"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/nomenclature/{id}")
    public Nomenclature nomenclature(
            @ApiParam(value = "идентификатор номенклатуры") @PathVariable("id") long id)
                throws NomenclatureIdNotFound {
        return nomenclatureService.findById(id);
    }

    @ApiOperation(
            value = "Создание новой номенклатуры",
            notes = "Создание новой номенклатуры на основании данных пользователя."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PostMapping(value = "rest-api/admin/nomenclature")
    public Nomenclature nomenclatureCreate(@RequestBody @Valid Nomenclature nomenclature)
            throws NomenclatureAlreadyExist {
        return nomenclatureService.create(nomenclature);
    }

    @ApiOperation(
            value = "Обновление номенклатуры",
            notes = "Обновление номенклатуры по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 404, message = "номенклатура по идентификатору не найдена"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping(value = "rest-api/admin/nomenclature/{id}")
    @ResponseBody
    public Nomenclature nomenclatureUpdateById(
            @ApiParam(value = "идентификатор номенклатуры") @PathVariable("id") long id,
            @PathVariable("id") Nomenclature nomenclatureFromDB,
            @RequestBody @Valid Nomenclature nomenclature)
            throws NomenclatureIdNotFound {
        return nomenclatureService.update(id, nomenclatureFromDB, nomenclature);
    }

}
