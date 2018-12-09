package com.lpdm.msstorage.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "file_type", schema = "public")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false, length = 10)
    private String type;

    public Type() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Type)) return false;
        Type type1 = (Type) o;
        return getId() == type1.getId() &&
                Objects.equals(getType(), type1.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getType());
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
