package com.lpdm.msstorage.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class UserException extends RuntimeException {

    public UserException(){
        super("Bad request : User object not found !");
    }
}
