package com.blawniczak.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
public class TicketForm {

    private Long ticketType;
    private boolean halfPrice;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

}
