package com.onlineshop.controllers;

import com.onlineshop.domain.Role;
import com.onlineshop.domain.User;
import com.onlineshop.repositorys.UserRepo;
import com.onlineshop.services.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller //TODO переделать на рест контроллер
@RequestMapping("registration")
public class RegistrationController {

    private final UserDetailService userSevice;

    @Autowired
    public RegistrationController(UserDetailService userSevice) {
        this.userSevice = userSevice;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addNewUser(User user) {

        userSevice.addUser(user); //TODO если пользователь с таким именен ужеть необходимо выдать сообщение об ошибке
        return "redirect:login";
    }
}
