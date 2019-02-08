package com.onlineshop.controller;

import com.onlineshop.dao.entitys.Role;
import com.onlineshop.dao.entitys.User;
import com.onlineshop.dto.UserRequest;
import com.onlineshop.dto.UserRequestAdmin;
import com.onlineshop.dto.UserResponse;
import com.onlineshop.exception.UserAlreadyExist;
import com.onlineshop.exception.UserNotFound;
import com.onlineshop.service.UserDetailService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/admin/users")
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
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 404, message = "пользователь по идентификатору не найден"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/admin/user/{id}")
    public UserResponse userById(
            @ApiParam(value = "идентификатор пользователя") @PathVariable("id") long id,
            @PathVariable("id") @Valid  User user)
            throws UserNotFound {
        return userDetailService.findById(id, user);
    }

    @ApiOperation(
            value = "Информация о текущем пользователе.",
            notes = "Информация о текущем пользователе."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/user")
    public UserResponse authenticationUser(
            @AuthenticationPrincipal User user) {
        return userDetailService.authenticationUser(user);
    }

    @ApiOperation(
            value = "Обновление существующего пользователя",
            notes = "Обновление пользователя по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 404, message = "пользователь по идентификатору не найден"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping(value = "rest-api/user")
    public UserResponse authenticationUserUpdate(
            @AuthenticationPrincipal User userFromDB,
            @RequestBody @Valid UserRequest userRequest)
            throws UserNotFound {
        return userDetailService.authenticationUserUpdate(userFromDB, userRequest);
    }

    @ApiOperation(
            value = "Обновление существующего пользователя",
            notes = "Обновление пользователя по идентификатору."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 404, message = "пользователь по идентификатору не найден"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @PutMapping(value = "rest-api/admin/user/{id}")
    public UserResponse userUpdate(
            @ApiParam(value = "идентификатор пользователя") @PathVariable("id") long id,
            @PathVariable("id") User userFromDB,
            @RequestBody @Valid UserRequestAdmin userRequestAdmin)
            throws UserNotFound {
        return userDetailService.updateAdmin(id, userFromDB, userRequestAdmin);
    }

    @ApiOperation(
            value = "Спиок всех ролей сервиса",
            notes = "Получение списока всех ролей сервиса."
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "операция прошла успешно"),
            @ApiResponse(code = 400, message = "запрос неверно сформирован"),
            @ApiResponse(code = 401, message = "ошибка авторизации"),
            @ApiResponse(code = 403, message = "доступ запрещен"),
            @ApiResponse(code = 500, message = "внутренняя ошибка сервера"),
    })
    @GetMapping(value = "rest-api/admin/roles")
    public List<Role> getAllRoles(){
        return userDetailService.getAllRoles();
    }

}
