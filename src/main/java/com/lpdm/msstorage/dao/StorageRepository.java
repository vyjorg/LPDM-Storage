package com.lpdm.msstorage.dao;

import com.lpdm.msstorage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Integer> {

    List<Storage> findAllByOwner(int owner);
}
