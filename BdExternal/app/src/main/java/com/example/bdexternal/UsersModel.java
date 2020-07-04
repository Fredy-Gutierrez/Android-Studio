package com.example.bdexternal;

public class UsersModel {

    private int id;
    private String user;
    private String pass;

    public UsersModel() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public UsersModel(int id, String user, String pass) {
        this.id = id;
        this.user = user;
        this.pass = pass;
    }
}
