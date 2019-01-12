package com.onlineshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("onlineshop")
public class OnlineShopController {

    @GetMapping
    public String IndexPage() {
        return "Перейти к покупке товаров";
    }
}
