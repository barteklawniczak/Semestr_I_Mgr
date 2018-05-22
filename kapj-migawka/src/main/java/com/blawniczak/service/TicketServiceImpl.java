package com.blawniczak.service;

import com.blawniczak.domain.Card;
import com.blawniczak.domain.Ticket;
import com.blawniczak.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketRepository ticketRepository;

    public List<Ticket> findAll() { return ticketRepository.findAll(); }

    public void save(Ticket ticket) { ticketRepository.save(ticket); }

    @Override
    public Optional<Ticket> findById(Long id) { return ticketRepository.findById(id); }
}
