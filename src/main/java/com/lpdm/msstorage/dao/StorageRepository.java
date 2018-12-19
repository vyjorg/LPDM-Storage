package com.lpdm.msstorage.dao;

import com.lpdm.msstorage.entity.Storage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Integer> {

    List<Storage> findAllByOwner(int owner);
    Optional<Storage> findByOwnerAndUrl(int owner, String url);
}
