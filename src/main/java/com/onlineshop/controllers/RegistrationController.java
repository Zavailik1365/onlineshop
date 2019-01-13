package com.onlineshop.controllers;

import com.onlineshop.domain.Role;
import com.onlineshop.domain.User;
import com.onlineshop.repositorys.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;

@Controller //TODO переделать на рест контроллер
@RequestMapping("registration")
public class RegistrationController {

    private final UserRepo userRepo;

    @Autowired
    public RegistrationController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String addNewUser(User user) {

        User userFromDB = userRepo.findByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
        } else {
            // TODO сообщение о том что юзер уже есть.
        }
        return "redirect:login";
    }
}
