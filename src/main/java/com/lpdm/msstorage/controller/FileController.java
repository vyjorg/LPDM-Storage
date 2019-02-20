package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.dao.StorageRepository;
import com.lpdm.msstorage.entity.Storage;
import com.lpdm.msstorage.entity.User;
import com.lpdm.msstorage.exception.FileNotFoundException;
import com.lpdm.msstorage.exception.UserException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @author Kybox
 * @version 1.0
 * @since 01/12/2018
 */

@RestController
@RequestMapping("/user")
@Api(tags = {"Storage Rest API"})
public class FileController {

    private Logger log = LogManager.getLogger(this.getClass());
    private final StorageRepository storageRepository;

    @Autowired
    public FileController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    /**
     * Get all {@link Storage} objects from a {@link User} id
     * @param id The {@link User} id
     * @return The {@link Storage} {@link List} from the {@link User}
     */
    @ApiOperation(value = "Get all Storage objects from a User id")
    @GetMapping(value = {"/{id}", "/{id}/"})
    public ResponseEntity<List<Storage>> getFilesByOwner(@PathVariable int id){

        List<Storage> storageList = storageRepository.findAllByOwner(id);
        if (storageList.isEmpty()) throw new FileNotFoundException();
        return ResponseEntity.ok().body(storageList);
    }

    /**
     * Get the latest uploaded file of a {@link User} by its id
     * @param id The {@link User} id
     * @return The latest {@link Storage} object from the {@link User}
     */
    @ApiOperation(value = "Get the latest uploaded file of a user by its id")
    @GetMapping(value = {"/{id}/latest", "/{id}/latest/"})
    public Storage getLastFileByOwner(@PathVariable int id){

        if(id == 0) throw new UserException();
        Optional<Storage> optStorage = storageRepository.findFirstByOwnerOrderByIdDesc(id);
        if(!optStorage.isPresent()) throw new UserException();
        return optStorage.get();
    }

    /**
     * Delete a {@link Storage} object with the {@link User} id, the folder and the file
     * @param id The {@link User} id
     * @param folder The folder name
     * @param file The file name
     * @return The {@link Storage} {@link List} objects without the deleted file
     */
    @ApiOperation(value = "Delete a Storage object from a user")
    @GetMapping(value = "/{id}/delete/{folder}/{file}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Storage>> deleteFile(@PathVariable(name = "id") int id,
                                                    @PathVariable(name = "folder") String folder,
                                                    @PathVariable(name = "file") String file){

        String url = "https://files.lpdm.kybox.fr/" + folder + "/" + file;
        log.info("File to delete : " + url);
        Optional<Storage> storage = storageRepository.findByOwnerAndUrl(id, url);
        if(!storage.isPresent()) throw new FileNotFoundException();

        storageRepository.delete(storage.get());

        List<Storage> storageList = storageRepository.findAllByOwner(id);
        return ResponseEntity.ok(storageList);
    }
}
