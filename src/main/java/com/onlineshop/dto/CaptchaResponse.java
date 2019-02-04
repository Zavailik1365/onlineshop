package com.onlineshop.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponse {

    /**
     * Переименование свойства error-codes -> errorCodes
     */
    @JsonAlias("error-codes")
    private Set<String> errorCodes;
    private boolean success;

}
