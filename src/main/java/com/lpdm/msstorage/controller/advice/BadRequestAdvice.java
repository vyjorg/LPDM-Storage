package com.lpdm.msstorage.controller.advice;

import com.lpdm.msstorage.exception.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@ControllerAdvice
public class BadRequestAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserException.class)
    String badRequestHandler(UserException e){
        return e.getMessage();
    }
}
