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
    public String index(Model model, @AuthenticationPrincipal User user){

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "index";
    }

    @GetMapping(value = "user")
    public String user(Model model, @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "user";
    }

    @GetMapping(value = "admin/user/{id}")
    public String user(
            @PathVariable long id,
            Model model,
            @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        profileData.put("userId", id);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "user";
    }

    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accesssDenied() {
          return "403";
    }

    @GetMapping(value = "admin/users")
    public String users(Model model, @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "users";
    }

    @GetMapping(value = "admin/sales")
    public String sales(Model model, @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "sales";
    }

    @GetMapping(value = "purchase")
    public String makingPurchase(Model model, @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "purchase";
    }

    @GetMapping(value = "admin/sale")
    public String sale(Model model, @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "sale";
    }

    @GetMapping(value = "admin/sale/{id}")
    public String saleById(
            @PathVariable String id,
            Model model,
            @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        profileData.put("saleId", id);

        HashMap<Object, Object> frontendData = new HashMap<>();
        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "sale";
    }

    @GetMapping(value = "admin/nomenclatures")
    public String nomenclatures(Model model, @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "nomenclatures";
    }

    @GetMapping(value = "admin/nomenclature/{id}")
    public String nomenclature(
            @PathVariable String id,
            Model model,
            @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        profileData.put("nomenclatureId", id);

        HashMap<Object, Object> frontendData = new HashMap<>();
        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "nomenclature";
    }

    @GetMapping(value = "admin/nomenclature")
    public String addNomenclature(
            Model model,
            @AuthenticationPrincipal User user) {

        HashMap<String, Object>profileData = newprofileData(user);
        HashMap<Object, Object> frontendData = new HashMap<>();

        frontendData.put("profile", profileData);
        model.addAttribute("frontendData", frontendData);

        return "nomenclature";
    }

    private HashMap<String, Object> newprofileData(User user){

        HashMap<String, Object> profileData = new HashMap<>();

        if (user == null) {
            profileData.put("isAdmin",  false);
            profileData.put("name",     "Unknow");

        }else {
            profileData.put("isAdmin", user.getRoles().contains(Role.ROLE_ADMIN));
            profileData.put("name",    user.getUsername());
       }

       return profileData;
    }
}
