package com.onlineshop.controllers;

import com.onlineshop.domain.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class Main {

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

}
