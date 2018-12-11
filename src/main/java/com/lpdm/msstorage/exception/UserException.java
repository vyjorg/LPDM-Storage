package com.lpdm.msstorage.exception;

public class UserException extends RuntimeException {

    public UserException(){
        super("Bad request : User object not found !");
    }
}
