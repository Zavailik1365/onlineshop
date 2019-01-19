package com.onlineshop.rest.controller;

import com.onlineshop.rest.exception.NomenclatureIdNotFound;
import com.onlineshop.rest.exception.SaleNotFound;
import com.onlineshop.rest.exception.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Collections;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler({NomenclatureIdNotFound.class, UserNotFound.class, SaleNotFound.class})
    public ResponseEntity<ExceptionResponse> handleEntityNotFoundExceptionException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse();
        response.setErrors(Collections.singletonList(ex.getMessage()));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    class ExceptionResponse {
        private List<String> errors;

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }
    }
}
