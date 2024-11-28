/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entities;

import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author sarai
 */
public class User {

    private ObjectId id;
    private String fullname;
    private String email;
    private String username;
    private String password;

    public User() {
    }

    public User(String fullname, String email, String username, String password) {
        this.fullname = fullname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{_id=" + id + "fullname=" + fullname + ", email=" + email + ", username=" + username + "}";
    }

    public Document toDocument() {
        return new Document("fullname", fullname)
                .append("email", email)
                .append("username", username)
                .append("password", password);
    }
}
