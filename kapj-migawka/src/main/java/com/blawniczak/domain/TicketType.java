package com.blawniczak.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "TICKET_TYPES")
public class TicketType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TICKET_TYPE_ID")
    private Long Id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PRICE")
    private float price;

    @Column(name = "DURATION_IN_DAYS")
    private Long duration;
}
