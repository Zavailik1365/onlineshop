package com.onlineshop.rest.controller;

import com.onlineshop.rest.dao.entitys.User;
import com.onlineshop.rest.dto.UserResponse;
import com.onlineshop.rest.exception.UserNotFound;
import com.onlineshop.rest.service.UserDetailService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@Api(description = "Работа с пользователями",
        consumes = "application/json")
public class UserController {

    private final UserDetailService userDetailService;

    @Autowired
    public UserController(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @ApiOperation(
            value = "Список всех пользоваителей сервиса",
            notes = "Информация о всех пользователях системы."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("admin/users")
    public List<UserResponse> usersList() {
        return userDetailService.findAll();
    }

    @ApiOperation(
            value = "Информация о пользователе по идентификатору.",
            notes = "Информация о пользователе по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "пользователь по идентификатору не найден"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping("admin/users/{id}")
    @ResponseBody
    public UserResponse usersById(
            @ApiParam(value = "идентификатор пользователя", required = true) @PathVariable("id") long id)
            throws UserNotFound {
        return userDetailService.findById(id);
    }

    @ApiOperation(
            value = "Создание нового пользователя",
            notes = "Создание нового пользователя."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 403, message = "недостаточно прав для создания новго пользователя"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PostMapping("admin/user")
    public UserResponse userCreate(@RequestBody User user) {
        return userDetailService.create(user);
    }

    @ApiOperation(
            value = "Обновление существующего пользователя",
            notes = "Обновление пользователя по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "пользователь по идентификатору не найден"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping("admin/user/{id}")
    @ResponseBody
    public UserResponse nomenclatureUpdate(
            @ApiParam(value = "идентификатор пользователя", required = true) @PathVariable("id") long id,
            @PathVariable("id") User nomenclatureFromDB,
            @RequestBody User nomenclature)
            throws UserNotFound {
        return userDetailService.update(id, nomenclatureFromDB, nomenclature);
    }

    @ApiOperation(
            value = "Удаление пользователя",
            notes = "Удаление пользователя по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "пользователь по идентификатору не найден"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @DeleteMapping("admin/user/{id}")
    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public void nomenclatureDelete(
            @ApiParam(value = "идентификатор номенклатуры", required = true) @PathVariable("id") long id,
            @PathVariable("id") User nomenclatureFromDB)
            throws UserNotFound {
        userDetailService.delete(id, nomenclatureFromDB);
    }
}
