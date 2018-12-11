package com.lpdm.msstorage.entity;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
