package com.onlineshop.rest.controller;

import com.onlineshop.rest.exception.NomenclatureIdNotFound;
import com.onlineshop.rest.exception.SaleNotFound;
import com.onlineshop.rest.exception.UserAlreadyExist;
import com.onlineshop.rest.exception.UserNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    private final static Logger LOGGER = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler({NomenclatureIdNotFound.class,
            UserNotFound.class,
            SaleNotFound.class,
            UserAlreadyExist.class})
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundExceptionException(Exception exception) {
        LOGGER.warn(exception.getMessage());
        ExceptionResponse response = new ExceptionResponse();
        response.setErrors(Collections.singletonList(exception.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    class ExceptionResponse {
        private List<String> errors;

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }
}
