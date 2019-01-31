package com.onlineshop.controller;

import com.onlineshop.dao.entitys.Role;
import com.onlineshop.dao.entitys.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user){
        newFrontEndData(model, user, "");
        return "index";
    }

    @GetMapping(value = "user")
    public String user(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "");
        return "user";
    }

    @GetMapping(value = "admin/user/{id}")
    public String user(@PathVariable long id,  Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, String.valueOf(id));
        return "user";
    }

    @GetMapping(value = "admin/users")
    public String users(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "");
        return "users";
    }

    @GetMapping(value = "sales")
    public String sales() {
        return "sales";
    }

    @GetMapping(value = "nomenclatures")
    public String nomenclatures() {
        return "nomenclatures";
    }

    private void newFrontEndData(Model model, User user, String id){

        HashMap<String, Object> profileData = new HashMap<>();

        if (user == null) {
            profileData.put("isAdmin",  false);
            profileData.put("name",     "Unknow");
            profileData.put("id",       id);
        }else {
            profileData.put("isAdmin", user.getRoles().contains(Role.ROLE_ADMIN));
            profileData.put("name",    user.getUsername());
            profileData.put("id",      id.equals("") ? user.getId(): id);
        }

        HashMap<Object, Object> frontendData = new HashMap<>();
        frontendData.put("profile", profileData);

        model.addAttribute("frontendData", frontendData);
    }
}
