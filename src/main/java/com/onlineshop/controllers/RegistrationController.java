package com.onlineshop.controllers;

import com.onlineshop.dao.entitys.User;
import com.onlineshop.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //TODO переделать на рест контроллер
@RequestMapping("registration")
public class RegistrationController {

    private final UserDetailService userService;

    @Autowired
    public RegistrationController(UserDetailService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addNewUser(User user) {

        userService.addUser(user); //TODO если пользователь с таким именен ужеть необходимо выдать сообщение об ошибке
        return "redirect:login";
    }
}
