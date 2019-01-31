package com.onlineshop.exception;

public class CaptchaIsNotSuccess extends Exception {

    private final static String MESSAGE_TEMPLATE =
            "Не верно введена каптча.";

    public CaptchaIsNotSuccess() {
        super(MESSAGE_TEMPLATE);
    }
}
