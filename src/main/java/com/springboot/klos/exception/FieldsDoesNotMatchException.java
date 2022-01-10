package com.springboot.klos.exception;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FieldsDoesNotMatchException extends MethodArgumentNotValidException {
    public FieldsDoesNotMatchException(MethodParameter parameter, BindingResult bindingResult) {
        super(parameter, bindingResult);
    }
}
