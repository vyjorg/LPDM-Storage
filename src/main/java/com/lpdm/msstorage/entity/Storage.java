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

    @OneToOne
    @JoinColumn(name = "type_id")
    private Type type;

    @NotNull
    private int owner;

    @NotNull
    private String url;

    public Storage() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
                Objects.equals(getType(), storage.getType()) &&
                Objects.equals(getUrl(), storage.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType(), getOwner(), getUrl());
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", type=" + type +
                ", owner=" + owner +
                ", url='" + url + '\'' +
                '}';
    }
}
