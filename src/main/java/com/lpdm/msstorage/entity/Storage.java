package com.lpdm.msstorage.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "storage", schema = "public")
public class Storage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String fileType;

    @NotNull
    private int owner;

    @NotNull
    private String url;

    public Storage() {
    }

    public Storage(String fileType) {
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Storage)) return false;
        Storage storage = (Storage) o;
        return getId() == storage.getId() &&
                getOwner() == storage.getOwner() &&
                Objects.equals(getFileType(), storage.getFileType()) &&
                Objects.equals(getUrl(), storage.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFileType(), getOwner(), getUrl());
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", fileType=" + fileType +
                ", owner=" + owner +
                ", url='" + url + '\'' +
                '}';
    }
}
