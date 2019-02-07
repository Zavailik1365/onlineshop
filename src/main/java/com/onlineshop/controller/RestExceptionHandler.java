package com.onlineshop.controller;

import com.onlineshop.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@ControllerAdvice(annotations = RestController.class)
public class RestExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(
            {
                    NomenclatureIdNotFound.class,
                    SaleNotFound.class,
                    UserNotFound.class,
                    NomenclatureIdNotFoundList.class
            })
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleNOT_FOUND(Exception exception) {
        LOGGER.warn(exception.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrors(Collections.singletonList(exception.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(
            {
                    UserAlreadyExist.class,
                    NomenclatureAlreadyExist.class
            })
    @ResponseBody
    public ResponseEntity<ExceptionResponse> handleCONFLICT (Exception exception) {
        LOGGER.warn(exception.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrors(Collections.singletonList(exception.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    private class ExceptionResponse {
        private List<String> errors;

        private void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }
}
