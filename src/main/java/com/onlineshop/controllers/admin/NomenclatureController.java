package com.onlineshop.controllers.admin;

import com.onlineshop.domain.Nomenclature;
import com.onlineshop.repositorys.NomenclatureRepo;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("online-shop/admin/nomenclature")
public class NomenclatureController {

    private final NomenclatureRepo nomenclatureRepo;

    public NomenclatureController(NomenclatureRepo nomenclatureRepo) {
        this.nomenclatureRepo = nomenclatureRepo;
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
    @GetMapping
    public List<Nomenclature> list() {
        return nomenclatureRepo.findAll();
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
    @PostMapping
    public Nomenclature create(@RequestBody Nomenclature nomenclature) {
        return nomenclatureRepo.save(nomenclature);
    }

    @ApiOperation(
            value = "Обновление номенклатуры",
            notes = "Обновление номенклатуры по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping("{id}")
    public Nomenclature update(
            @PathVariable("id") Nomenclature nomenclatureFromDB,
            @RequestBody Nomenclature nomenclature) {

        BeanUtils.copyProperties(
                nomenclature,
                nomenclatureFromDB,
                "id");

        return nomenclatureRepo.save(nomenclatureFromDB);
    }

    @ApiOperation(
            value = "Удаление номенклатуры",
            notes = "Удаление номенклатуры по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @DeleteMapping("{id}")
    @ExceptionHandler(value = { Exception.class })
    public void delete(@PathVariable("id") Nomenclature nomenclatureFromDB) {
        nomenclatureRepo.delete(nomenclatureFromDB);
    }
}
