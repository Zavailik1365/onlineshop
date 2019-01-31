package com.onlineshop.controller;

import com.onlineshop.dto.CaptchaResponse;
import com.onlineshop.dto.UserRequest;
import com.onlineshop.exception.CaptchaIsNotSuccess;
import com.onlineshop.exception.UserAlreadyExist;
import com.onlineshop.service.UserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

    @Value("${recaptcha.secret}")
    private String secret;
    private final static String CAPCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";
    private final UserDetailService userService;
    private final RestTemplate restTemplate;


    @Autowired
    public RegistrationController(UserDetailService userService, RestTemplate restTemplate) {
        this.userService = userService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String registration() {
        return "registration";
    }

    @PostMapping
    public String registration(
                @RequestParam("g-recaptcha-response") String captchaResponse,
                @Valid UserRequest userRequest)
            throws UserAlreadyExist, CaptchaIsNotSuccess {
        String url = String.format(CAPCHA_URL, secret, captchaResponse);
        CaptchaResponse captchaResponseDto = restTemplate.postForObject(
                    url,
                    Collections.emptyList(),
                    CaptchaResponse.class);
        if (!captchaResponseDto.isSuccess()) {
            throw new CaptchaIsNotSuccess();
        }
        userService.addUser(userRequest);
        return "redirect:login";
    }
}
