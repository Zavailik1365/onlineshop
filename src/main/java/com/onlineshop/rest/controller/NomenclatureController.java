package com.onlineshop.rest.controller;

import com.onlineshop.rest.dao.entitys.Nomenclature;
import com.onlineshop.rest.exception.NomenclatureIdNotFound;
import com.onlineshop.rest.service.NomenclatureService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @ApiResponse(code = 403, message = "отсутствуют права на редактирование номенклатуры"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("nomenclatures")
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
            @ApiResponse(code = 403, message = "отсутствуют права на получение номенклатуры"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("nomenclatures/{id}")
    public Nomenclature nomenclature(
            @ApiParam(value = "идентификатор номенклатуры", required = true) @PathVariable("id") long id)
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
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PostMapping("admin/nomenclatures")
    public Nomenclature nomenclatureCreate(@RequestBody Nomenclature nomenclature) {
        return nomenclatureService.create(nomenclature);
    }

    @ApiOperation(
            value = "Обновление номенклатуры",
            notes = "Обновление номенклатуры по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "номенклатура по идентификатору не найдена"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping("admin/nomenclature/{id}")
    @ResponseBody
    public Nomenclature nomenclatureUpdateById(
            @ApiParam(value = "идентификатор номенклатуры", required = true) @PathVariable("id") long id,
            @PathVariable("id") Nomenclature nomenclatureFromDB,
            @RequestBody Nomenclature nomenclature)
            throws NomenclatureIdNotFound {
        return nomenclatureService.update(id, nomenclatureFromDB, nomenclature);
    }

    @ApiOperation(
            value = "Удаление номенклатуры",
            notes = "Удаление номенклатуры по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "номенклатура по идентификатору не найдена"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @DeleteMapping("admin/nomenclatures/{id}")
    @ExceptionHandler(value = { Exception.class })
    public void nomenclatureDelete(
            @ApiParam(value = "идентификатор номенклатуры", required = true) @PathVariable("id") long id,
            @PathVariable("id") Nomenclature nomenclatureFromDB)
            throws NomenclatureIdNotFound {
        nomenclatureService.delete(id, nomenclatureFromDB);
    }

}
