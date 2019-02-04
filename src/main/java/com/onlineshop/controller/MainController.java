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
        newFrontEndData(model, user, "", "", "");
        return "index";
    }

    @GetMapping(value = "user")
    public String user(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", "");
        return "user";
    }

    @GetMapping(value = "admin/user/{id}")
    public String user(
            @PathVariable long id,
            Model model,
            @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, String.valueOf(id), "", "");
        return "user";
    }

    @GetMapping(value = "admin/users")
    public String users(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", "");
        return "users";
    }

    @GetMapping(value = "admin/sales")
    public String sales(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", "id");
        return "sales";
    }

    @GetMapping(value = "sale")
    public String sale(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", "");
        return "sale";
    }

    @GetMapping(value = "admin/sale/{id}")
    public String saleById(
            @PathVariable String id,
            Model model,
            @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", id);
        return "sale";
    }

    @GetMapping(value = "admin/nomenclatures")
    public String nomenclatures(Model model, @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", "");
        return "nomenclatures";
    }

    @GetMapping(value = "admin/nomenclature/{id}")
    public String nomenclature(
            @PathVariable String id,
            Model model,
            @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", id, "");
        return "nomenclature";
    }

    @GetMapping(value = "admin/nomenclature")
    public String addNomenclature(
            Model model,
            @AuthenticationPrincipal User user) {
        newFrontEndData(model, user, "", "", "");
        return "nomenclature";
    }

    private void newFrontEndData(
            Model model,
            User user,
            String id,
            String nomenclatureId,
            String saleId){

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

        profileData.put("nomenclatureId", nomenclatureId);
        profileData.put("saleId",         saleId);

        HashMap<Object, Object> frontendData = new HashMap<>();
        frontendData.put("profile", profileData);

        model.addAttribute("frontendData", frontendData);
    }
}
