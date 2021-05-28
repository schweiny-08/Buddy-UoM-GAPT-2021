package com.example.buddypersonal;

import java.util.ArrayList;

public class User {

    private String uName, uSurname, uDob;

    private Integer user_Id, telephone, role_Id;
    private String username, email, password;

    public Integer getId() {
        return user_Id;
    }

    public Integer getTelephone() {
        return telephone;
    }

    public Integer getRole_id() {
        return role_Id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String temp) {this.username = temp;}

    public void setName(String temp) {this.uName = temp;}

    public void setEmail(String temp) {this.email = temp;}

    public void setSurname(String temp) {this.uSurname = temp;}

    public void setPassword(String temp) {
        this.password = temp;
    }

    public User(Integer user_Id, String username, Integer telephone, String email, String password, Integer role_Id, String name, String surname, String doby) {
        this.user_Id = user_Id;
        this.telephone = telephone;
        this.role_Id = role_Id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.uDob = doby;
        this.uName = name;
        this.uSurname = surname;
    }

    public User() {
        this.user_Id = -1;
        this.telephone = 0;
        this.role_Id = 0;
        this.username = "default_user";
        this.email = "default_email@buddy.com";
        this.password = "default_password";
        this.uName = "def";
        this.uSurname = "def";
        this.uDob = "01/01/70"; //the beginning of recorded history
    }

    public String getUName() {
       return this.uName;
    }

    public String getUSurname() {
        return uSurname;
   }

    public String getUDob() {
        return uDob;
    }

}
