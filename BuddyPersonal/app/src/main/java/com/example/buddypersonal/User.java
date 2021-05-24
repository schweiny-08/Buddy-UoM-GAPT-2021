package com.example.buddypersonal;

import java.util.ArrayList;

public class User {

    //private Integer uId;
    //private String uName, uSurname, uDob, uEmail, uUsername, uPassword;

    private Integer user_Id, telephone, role_Id;
    private String username, email, password;

    //public ArrayList<EventModel> privateEvents = new ArrayList<EventModel>();

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

    public User(Integer user_Id, String username, Integer telephone, String email, String password, Integer role_Id) {
        this.user_Id = user_Id;
        this.telephone = telephone;
        this.role_Id = role_Id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
        this.user_Id = -1;
        this.telephone = 0;
        this.role_Id = 0;
        this.username = "default_user";
        this.email = "default_email@buddy.com";
        this.password = "default_password";
    }

//    public User(Integer uID, String uName, String uSurname, String uDob, String uEmail, String uUsername, String uPassword) {
//        this.uId = uId;
//        this.uName = uName;
//        this.uSurname = uSurname;
//        this.uDob = uDob;
//        this.uEmail = uEmail;
//        this.uUsername = uUsername;
//        this.uPassword = uPassword;
//    }

//    public int getuId() {
//        return uId;
//    }
//
//    public String getUName() {
//        return uName;
//    }
//
//    public String getUSurname() {
//        return uSurname;
//    }
//
//    public String getUDob() {
//        return uDob;
//    }
//
//    public String getUEmail() {
//        return uEmail;
//    }
//
//    public String getUUsername() {
//        return uUsername;
//    }
//
//    public String getUPassword() {
//        return uPassword;
//    }
}
