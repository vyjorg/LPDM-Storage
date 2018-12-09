package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.entity.FileMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

@Controller
@RefreshScope
@RequestMapping("/fsfsdfsdfs")
public class UploadController {

    private Logger log = LogManager.getLogger(this.getClass());
    private LinkedList<FileMetaData> files = new LinkedList<>();
    private FileMetaData fileMetaData = null;

    @PostMapping(value = "upload")
    public @ResponseBody LinkedList<FileMetaData> upload(MultipartHttpServletRequest request, HttpServletResponse response){

        Iterator<String> iterator = request.getFileNames();
        MultipartFile multipartFile = null;

        while (iterator.hasNext()){

            multipartFile = request.getFile(iterator.next());
            log.info(multipartFile.getOriginalFilename() + " uploaded " + files.size());

            if(files.size() >= 10) files.pop();

            fileMetaData = new FileMetaData();
            fileMetaData.setFileName(multipartFile.getOriginalFilename());
            fileMetaData.setFileSize(multipartFile.getSize() / 1024 + " kb");
            fileMetaData.setFileType(multipartFile.getContentType());

            try {

                fileMetaData.setBytes(multipartFile.getBytes());

                FileCopyUtils.copy(multipartFile.getBytes(),
                        new FileOutputStream("storage/files" + multipartFile.getOriginalFilename()));

            }
            catch (IOException e) { log.error(e.getMessage()); }

            files.add(fileMetaData);
        }

        return files;
    }

    @GetMapping(value = "get/{value}")
    public void get(HttpServletResponse response, @PathVariable String value){
        FileMetaData fileMetaData = files.get(Integer.parseInt(value));
        try{

            response.setContentType(fileMetaData.getFileType());
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + fileMetaData.getFileName() + "\"");
            FileCopyUtils.copy(fileMetaData.getBytes(), response.getOutputStream());
        }
        catch (IOException e) { log.error(e.getMessage()); }
    }

    @GetMapping
    public String displayPage(){
        return "index";
    }
}
