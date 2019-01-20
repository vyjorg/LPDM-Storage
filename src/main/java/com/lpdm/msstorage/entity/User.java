package com.lpdm.msstorage.entity;

public class User {

    private int id;
    private boolean restricted;

    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isRestricted() {
        return restricted;
    }

    public void setRestricted(boolean restrict) {
        this.restricted = restrict;
    }
}
