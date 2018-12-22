package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.dao.StorageRepository;
import com.lpdm.msstorage.entity.FileUploadForm;
import com.lpdm.msstorage.entity.Storage;
import com.lpdm.msstorage.entity.User;
import com.lpdm.msstorage.exception.UserException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Controller
@RefreshScope
@RequestMapping("/")
public class UploadController {

    private Logger log = LogManager.getLogger(this.getClass());
    private final StorageRepository storageRepository;

    @Autowired
    public UploadController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @GetMapping("test")
    public String displayForm() {
        return "fileUploadForm";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String sendForm(@RequestBody User user, Model model){

        if(user.getId() != 0) {
            model.addAttribute("user", user.getId());
            return "fileUploadForm";
        }
        else throw new UserException();
    }

    @CrossOrigin
    @PostMapping(value = "save")
    public ResponseEntity<Object> save(@ModelAttribute("uploadForm")FileUploadForm uploadForm){

        log.info("USER ID = " + uploadForm.getUser());
        List<MultipartFile> files = uploadForm.getFiles();
        List<String> fileNames = new ArrayList<>();


        if (uploadForm.getUser().isEmpty() || uploadForm.getUser().equals("0") || uploadForm.getUser().equals("null"))
            throw new UserException();

        if(files != null && files.size() > 0){

            int nbFiles = 0;

            String userFolder = UUID.randomUUID().toString();

            new File("files/" + userFolder).mkdir();

            String folder = "files/" + userFolder + "/";

            ArrayList<String> uploadedFiles = new ArrayList<>();

            for(MultipartFile multipartFile : files){

                if(multipartFile != null && !Objects
                        .equals(multipartFile.getContentType(), "application/octet-stream")){

                    String fileName = multipartFile.getOriginalFilename();
                    fileNames.add(fileName);

                    String url = userFolder + "/" + fileName;

                    log.info("File : " + fileName);
                    log.info("Size :" + multipartFile.getSize());
                    log.info("ContentType :" + multipartFile.getContentType());

                    try {
                        BufferedOutputStream bos = new BufferedOutputStream(
                                new FileOutputStream(
                                        new File(folder + fileName)
                                )
                        );

                        bos.write(multipartFile.getBytes());
                        bos.flush();
                        bos.close();

                        Storage storage = new Storage(multipartFile.getContentType());
                        storage.setOwner(Integer.parseInt(uploadForm.getUser()));
                        storage.setUrl("https://files.lpdm.kybox.fr/" + url);

                        storageRepository.save(storage);

                        uploadedFiles.add(multipartFile.getOriginalFilename());

                    }
                    catch (IOException e) { e.printStackTrace(); }
                }
            }

            String response = null;
            if(uploadedFiles.size() > 1) response = uploadedFiles.size() + " fichiers tranférés.";
            else response = uploadedFiles.size() + " fichier transféré.";
            return new ResponseEntity<>(response,HttpStatus.OK);
        }
        else return new ResponseEntity<>("Aucun fichier sélectionné !", HttpStatus.BAD_REQUEST);
    }
}