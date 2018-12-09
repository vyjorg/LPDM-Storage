package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.entity.FileUploadForm;
import com.lpdm.msstorage.entity.FormDataWithFile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RefreshScope
@RequestMapping("/")
public class FileUploadController {

    private final String FILE_PATH = "files/";
    private Logger log = LogManager.getLogger(this.getClass());


    @GetMapping
    public String displayForm() {
        return "fileUploadForm";
    }

    @PostMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String sendForm(){

        return "fileUploadForm";
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Object> save(@ModelAttribute("uploadForm")FileUploadForm uploadForm, Model model){

        List<MultipartFile> files = uploadForm.getFiles();
        List<String> fileNames = new ArrayList<>();

        if(files != null && files.size() > 0){

            int nbFiles = files.size();

            for(MultipartFile multipartFile : files){

                String fileName = multipartFile.getOriginalFilename();
                fileNames.add(fileName);

                log.info("File : " + fileName);
                log.info("Size :" + multipartFile.getSize());
                log.info("ContentType :" + multipartFile.getContentType());

                // Handle file content - multipartFile.getInputStream();

                File file = new File(FILE_PATH + fileName);

                try {
                    file.createNewFile();
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(multipartFile.getBytes());
                    fileOutputStream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            String response = null;
            if(nbFiles > 1) response = nbFiles + " fichiers tranférés.";
            else response = "1 fichier transféré.";
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else return new ResponseEntity<>("Aucun fichier sélectionné !", HttpStatus.BAD_REQUEST);
    }
}