package com.blawniczak.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class EditUser {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    public void updateFields(User user) {
        this.id=user.getId();
        this.username=user.getUsername();
        this.name=user.getName();
        this.surname=user.getSurname();
        this.email=user.getEmail();
        this.birthDate=user.getBirthDate();
    }

}
