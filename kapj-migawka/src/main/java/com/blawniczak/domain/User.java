package com.blawniczak.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Getter
@Setter
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "USER_ID")
    private Long Id;
    @Column(name = "USERNAME", unique = true)
    private String username;
    @Column(name = "PASSWORD", nullable = false, updatable = false)
    private String password;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "SURNAME", nullable = false)
    private String surname;
    @Column(name = "BIRTH_DATE", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REGISTRATION_DATE")
    private Date registrationDate;
    @Column(name = "ENABLED", columnDefinition = "BIT default 0")
    private boolean enabled;
    @Column(name = "TOKEN", updatable = false, unique = true)
    private UUID token;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Card card;

    @Transient
    private String passwordConfirm;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "USERS_ROLES", joinColumns = {@JoinColumn(name = "USER_ID", nullable = false, updatable = false)},
            inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", nullable = false, updatable = false) })
    private Set<Role> roles = new HashSet<Role>(0);

    public void updateFields(EditUser editUser) {
        this.username=editUser.getUsername();
        this.name=editUser.getName();
        this.surname=editUser.getSurname();
        this.email=editUser.getEmail();
        this.birthDate=editUser.getBirthDate();
    }
}
