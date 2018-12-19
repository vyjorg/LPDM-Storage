package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.dao.StorageRepository;
import com.lpdm.msstorage.entity.Storage;
import com.lpdm.msstorage.exception.FileNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RefreshScope
@RequestMapping("/user")
@RestController
public class FileController {

    private Logger log = LogManager.getLogger(this.getClass());
    private final StorageRepository storageRepository;

    @Autowired
    public FileController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Storage>> getFilesByOwner(@PathVariable int id){

        List<Storage> storageList = storageRepository.findAllByOwner(id);
        if (storageList.isEmpty()) throw new FileNotFoundException();
        return ResponseEntity.ok().body(storageList);
    }

    @GetMapping("/{userId}/delete/{fileUrl}")
    public ResponseEntity<List<Storage>> deleteFile(@PathVariable(name = "userId") int userId,
                                                    @PathVariable(name = "fileUrl") String fileUrl){
        Optional<Storage> file = storageRepository.findByOwnerAndUrl(userId, fileUrl);
        if(!file.isPresent()) throw new FileNotFoundException();

        storageRepository.delete(file.get());

        List<Storage> storageList = storageRepository.findAllByOwner(userId);
        return ResponseEntity.ok(storageList);
    }
}
