package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.dao.StorageRepository;
import com.lpdm.msstorage.entity.FileUploadForm;
import com.lpdm.msstorage.entity.Storage;
import com.lpdm.msstorage.entity.User;
import com.lpdm.msstorage.exception.UserException;
import com.lpdm.msstorage.utils.FileExtension;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@Controller
@RefreshScope
@RequestMapping("/")
@Api(tags = {"Upload Rest API"})
public class UploadController {

    private final StorageRepository storageRepository;

    @Autowired
    public UploadController(StorageRepository storageRepository) {

        this.storageRepository = storageRepository;
    }

    /**
     * Test path to display the upload form
     * @return The html page with the upload form
     */
    @ApiOperation(value = "Test path to display the upload form")
    @GetMapping("test")
    public String displayForm() {
        return "fileUploadForm";
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ModelAndView sendForm(@RequestBody User user, Model model){

        if(user.getId() == 0)throw new UserException();
        model.addAttribute("user", user.getId());

        return new ModelAndView("fileUploadForm")
                .addObject("user", user.getId())
                .addObject("restricted", user.isRestricted());
    }

    /**
     * Save file(s) from the {@link FileUploadForm}
     * @param uploadForm The {@link FileUploadForm} with the data
     * @return The response of the upload process
     */
    @CrossOrigin
    @ApiOperation(value = "Save file(s) form the upload form")
    @PostMapping(value = "save")
    public ResponseEntity<Object> save(@ModelAttribute("uploadForm")FileUploadForm uploadForm){

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

                    //String fileName = multipartFile.getOriginalFilename();
                    String ext = FileExtension.get(multipartFile.getOriginalFilename()).get();
                    String fileName = UUID.randomUUID().toString() + ext;

                    fileNames.add(fileName);

                    String url = userFolder + "/" + fileName;

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