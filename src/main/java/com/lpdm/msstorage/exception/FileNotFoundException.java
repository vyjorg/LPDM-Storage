package com.lpdm.msstorage.exception;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(){
        super("No file found !");
    }
}
