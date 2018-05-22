package com.blawniczak.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "CARDS")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CARD_ID")
    private Long Id;

    @Column(name = "ENABLED", columnDefinition = "BIT default 0")
    private boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "card", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();
}
