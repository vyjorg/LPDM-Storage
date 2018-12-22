package com.lpdm.msstorage.controller;

import com.lpdm.msstorage.dao.StorageRepository;
import com.lpdm.msstorage.entity.Storage;
import com.lpdm.msstorage.utils.ObjToJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(FileController.class)
public class FileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StorageRepository storageDao;

    private int randomId;
    private Storage storage;
    private List<Storage> storageList;

    @Before
    public void init(){

        randomId = (int) (Math.random() * 123);

        storage = new Storage();
        storage.setOwner(1);
        storage.setUrl("https://files.lpdm.kybox.fr/myFoler/myFile.pdf");
        storage.setFileType("PDF");
        storage.setId(randomId);

        storageList = new ArrayList<>();
        storageList.add(storage);
    }

    @Test
    public void getFilesByOwner() throws Exception {

        Mockito.when(storageDao.findAllByOwner(Mockito.anyInt())).thenReturn(storageList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/" + randomId);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(storageList)))
                .andDo(print());
    }

    @Test
    public void deleteFile() throws Exception {

        Optional<Storage> optionalStorage = Optional.ofNullable(storage);
        Mockito.when(storageDao.findByOwnerAndUrl(Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(optionalStorage);

        Mockito.when(storageDao.findAllByOwner(Mockito.anyInt())).thenReturn(storageList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/" + randomId + "/delete/myFolder/myFile");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().json(ObjToJson.get(storageList)))
                .andDo(print());
    }
}
