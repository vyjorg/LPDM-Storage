package com.lpdm.msstorage.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

public class FileUploadForm {

    private List<MultipartFile> files;
    private String user;

    public FileUploadForm() {
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
