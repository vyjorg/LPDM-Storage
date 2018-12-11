package com.lpdm.msstorage.exception;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(){
        super("No file found !");
    }
}
