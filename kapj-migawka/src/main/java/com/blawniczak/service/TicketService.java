package com.blawniczak.service;

import com.blawniczak.domain.Ticket;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    List<Ticket> findAll();

    void save(Ticket ticket);

    Optional<Ticket> findById(Long id);

}
