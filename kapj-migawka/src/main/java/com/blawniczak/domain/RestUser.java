package com.blawniczak.domain;

import java.util.Date;

public class RestUser {

    private Long Id;
    private String username;
    private String name;
    private String surname;
    private Date birthDate;
    private String email;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void convertFields(User user) {
        this.birthDate=user.getBirthDate();
        this.email=user.getEmail();
        this.Id=user.getId();
        this.name=user.getName();
        this.surname=user.getSurname();
        this.username=user.getUsername();
    }
}
