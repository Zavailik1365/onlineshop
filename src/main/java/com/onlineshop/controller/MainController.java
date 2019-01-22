package com.onlineshop.controller;

import com.onlineshop.rest.dao.entitys.User;
import com.onlineshop.rest.exception.UserAlreadyExist;
import com.onlineshop.rest.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final UserDetailService userService;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){

        HashMap<Object, Object> data = new HashMap<>();
        if (user == null) {
            data.put("profile", null);
        }else {
            data.put("profile", user.getRoles());
        }
        model.addAttribute("frontendData", data);

        return "index";
    }

    @Autowired
    public MainController(UserDetailService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "registration")
    public String registration() {
        return "registration";
    }

    @PostMapping(value = "registration")
    public String addNewUser(User user) throws UserAlreadyExist {

        userService.addUser(user); //TODO переделать на REST
        return "redirect:login";
    }

    @GetMapping(value = "sales")
    public String sales() {
        return "sales";
    }
}
