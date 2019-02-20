package com.lpdm.msstorage.controller.advice;

import com.lpdm.msstorage.exception.FileNotFoundException;
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
public class NoFileAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(FileNotFoundException.class)
    String fileNotFoundHandler(FileNotFoundException e){
        return e.getMessage();
    }
}
