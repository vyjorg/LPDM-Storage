package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.dao.StorageRepository;
import com.lpdm.msstorage.entity.Storage;
import com.lpdm.msstorage.exception.FileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RefreshScope
@RequestMapping("/user")
@RestController
public class FileController {

    private final StorageRepository storageRepository;

    @Autowired
    public FileController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @GetMapping("/{id}")
    public List<Storage> getFilesByOwner(@PathVariable int id){

        List<Storage> storageList = storageRepository.findAllByOwner(id);
        if (storageList.isEmpty()) throw new FileNotFoundException();
        return storageList;
    }
}
