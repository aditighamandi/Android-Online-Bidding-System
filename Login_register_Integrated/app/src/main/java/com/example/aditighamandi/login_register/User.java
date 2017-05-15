package com.example.aditighamandi.login_register;

/**
 * Created by aditighamandi on 3/19/17.
 */

public class User {
    String name, username, password, category;

    public User(String name, String username, String password, String category){
        this.name = name;
        this.username = username;
        this.password = password;
        this.category = category;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.name = "";
        this.category="";
    }
}
